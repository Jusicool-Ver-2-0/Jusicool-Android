package com.jusicool.network.datasource.crypto.ws

import android.util.Log
import com.jusicool.model.crypto.CurrentCryptoPriceResponse
import com.jusicool.model.crypto.ws.UpbitTickerResponse
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.*
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
    }

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _tickerListFlow = MutableStateFlow<List<UpbitTickerResponse>>(emptyList())
    override val tickerListFlow: StateFlow<List<UpbitTickerResponse>> = _tickerListFlow.asStateFlow()

    private val sockets = mutableMapOf<Int, WebSocket>()

    private val adapter by lazy {
        moshi.adapter(UpbitTickerResponse::class.java)
    }

    override fun connect(markets: List<String>) {
        disconnect()

        val codes = markets.mapNotNull { it.trim().ifEmpty { null } }
        if (codes.isEmpty()) return

        val chunks = codes.chunked(MAX_CODES_PER_SOCKET)

        chunks.forEachIndexed { index, chunk ->
            val request = Request.Builder().url(ENDPOINT).build()

            val listener = object : WebSocketListener() {
                override fun onOpen(ws: WebSocket, response: Response) {
                    val payload = buildSubscribePayload(chunk)
                    ws.send(payload)
                    Log.d("$TAG[$index]", "구독 전송: $payload")
                }

                override fun onMessage(ws: WebSocket, text: String) {
                    parseAndEmit(text, index)
                }

                override fun onMessage(ws: WebSocket, bytes: ByteString) {
                    parseAndEmit(bytes.string(Charsets.UTF_8), index)
                }

                override fun onClosing(ws: WebSocket, code: Int, reason: String) {
                    ws.close(1000, null)
                    sockets.remove(index)
                    Log.d("$TAG[$index]", "closing: $reason")
                }

                override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                    sockets.remove(index)
                    Log.e("$TAG[$index]", "failure: ${t.message}", t)
                }
            }

            val ws = client.newWebSocket(request, listener)
            sockets[index] = ws
        }
    }

    override fun disconnect() {
        sockets.values.forEach { it.close(1000, "Client disconnect") }
        sockets.clear()
        _tickerListFlow.value = emptyList()
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
                    Log.w("UpbitWS[$index]", "파싱 실패: $message")
                }
            }
            .onFailure {
                Log.e("UpbitWS[$index]", "파싱 에러: ${it.message}", it)
            }
    }
}
