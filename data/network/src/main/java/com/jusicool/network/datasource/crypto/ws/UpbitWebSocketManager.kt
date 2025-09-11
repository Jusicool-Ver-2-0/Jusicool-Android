package com.jusicool.network.datasource.crypto.ws

import android.util.Log
import com.jusicool.model.crypto.ws.UpbitTickerResponse
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject
import javax.inject.Named

class UpbitWebSocketManager @Inject constructor(
    @Named("upbitWs") private val client: OkHttpClient,
    private val moshi: Moshi
) : UpbitWebSocketManagerInterface {

    companion object {
        private const val TAG = "UpbitWS"
        private const val ENDPOINT = "wss://api.upbit.com/websocket/v1"
        private const val MAX_CODES_PER_SOCKET = 200
        private const val BASE_DELAY_MS = 500L
        private const val MAX_DELAY_MS = 10_000L
        private const val MAX_EXP = 5
    }

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _tickerListFlow = MutableStateFlow<List<UpbitTickerResponse>>(emptyList())
    override val tickerListFlow: StateFlow<List<UpbitTickerResponse>> = _tickerListFlow.asStateFlow()

    private val sockets = mutableMapOf<Int, WebSocket>()
    private val chunksMap = mutableMapOf<Int, List<String>>()
    private val attempts = mutableMapOf<Int, Long>()

    private val adapter by lazy { moshi.adapter(UpbitTickerResponse::class.java) }

    override fun connect(markets: List<String>) {
        disconnect()
        val codes = markets.mapNotNull { it.trim().ifEmpty { null } }
        if (codes.isEmpty()) return
        val chunks = codes.chunked(MAX_CODES_PER_SOCKET)
        chunks.forEachIndexed { index, chunk ->
            chunksMap[index] = chunk
            attempts[index] = 0L
            openSocket(index, chunk)
        }
    }

    override fun disconnect() {
        sockets.values.forEach { it.close(1000, "Client disconnect") }
        sockets.clear()
        chunksMap.clear()
        attempts.clear()
        _tickerListFlow.value = emptyList()
    }

    private fun openSocket(index: Int, chunk: List<String>) {
        val request = Request.Builder().url(ENDPOINT).build()
        val listener = object : WebSocketListener() {
            override fun onOpen(ws: WebSocket, response: Response) {
                sockets[index] = ws
                attempts[index] = 0L
                val payload = buildSubscribePayload(chunk)
                ws.send(payload)
                Log.d("$TAG[$index]", "subscribe: $payload")
            }

            override fun onMessage(ws: WebSocket, text: String) {
                parseAndEmit(text, index)
            }

            override fun onMessage(ws: WebSocket, bytes: ByteString) {
                parseAndEmit(bytes.string(Charsets.UTF_8), index)
            }

            override fun onClosed(ws: WebSocket, code: Int, reason: String) {
                sockets.remove(index)
                Log.w("$TAG[$index]", "closed: code=$code reason=$reason")
                if (code != 1000) scheduleReconnect(index)
            }

            override fun onClosing(ws: WebSocket, code: Int, reason: String) {
                Log.d("$TAG[$index]", "closing: code=$code reason=$reason")
                ws.close(1000, null)
            }

            override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                sockets.remove(index)
                Log.e("$TAG[$index]", "failure: ${t.message}", t)
                scheduleReconnect(index)
            }
        }
        client.newWebSocket(request, listener)
    }

    private fun scheduleReconnect(index: Int) {
        val attempt = (attempts[index] ?: 0L) + 1L
        attempts[index] = attempt
        val factor = 1L shl attempt.coerceAtMost(MAX_EXP.toLong()).toInt()
        val delayMs = (BASE_DELAY_MS * factor).coerceAtMost(MAX_DELAY_MS)
        scope.launch {
            delay(delayMs)
            val chunk = chunksMap[index] ?: return@launch
            openSocket(index, chunk)
        }
    }

    private fun buildSubscribePayload(codes: List<String>): String {
        val codesJson = codes.joinToString(prefix = "[", postfix = "]") { "\"$it\"" }
        return """
            [
              {"ticket":"jusicool-${System.currentTimeMillis()}"},
              {"type":"ticker","codes":$codesJson},
              {"format":"DEFAULT"}
            ]
        """.trimIndent()
    }

    private fun parseAndEmit(message: String, index: Int) {
        runCatching { adapter.fromJson(message) }
            .onSuccess { parsed ->
                if (parsed != null) {
                    scope.launch {
                        _tickerListFlow.update { current ->
                            val mutable = current.toMutableList()
                            val at = mutable.indexOfFirst { it.market == parsed.market }
                            if (at >= 0) mutable[at] = parsed else mutable.add(parsed)
                            mutable
                        }
                    }
                } else {
                    Log.w("$TAG[$index]", "parse null: $message")
                }
            }
            .onFailure {
                Log.e("$TAG[$index]", "parse error: ${it.message}", it)
            }
    }
}
