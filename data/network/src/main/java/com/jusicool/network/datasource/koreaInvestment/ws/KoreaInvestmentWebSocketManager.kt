package com.jusicool.network.datasource.koreaInvestment.ws

import android.util.Log
import com.jusicool.model.koreaInvestment.ws.StockPriceSummary
import com.jusicool.network.util.KoreaInvestmentAuthManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Named

class KoreaInvestmentWebSocketManager @Inject constructor(
    @Named("koreaInvestmentOkHttpClient") private val client: OkHttpClient,
    private val authManager: KoreaInvestmentAuthManager
) : KoreaInvestmentWebSocketManagerInterface {

    private var webSocket: WebSocket? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _stockTickerListFlow = MutableStateFlow<List<StockPriceSummary>>(emptyList())
    override val stockTickerMapFlow: StateFlow<List<StockPriceSummary>> = _stockTickerListFlow.asStateFlow()

    override fun connect(stockCodes: List<String>) {
        if (webSocket != null) {
            disconnect()
        }

        scope.launch {
            runCatching { authManager.getApprovalKey() }
                .onSuccess { approvalKey ->
                    val request = Request.Builder()
                        .url("ws://ops.koreainvestment.com:21000/tryitout/H0STCNT0")
                        .build()

                    webSocket = client.newWebSocket(request, object : WebSocketListener() {

                        override fun onOpen(ws: WebSocket, response: Response) {
                            stockCodes.forEach { code ->
                                val msg = createSubscribeMessage(approvalKey, code)
                                ws.send(msg)
                                Log.d("WebSocket", "✅ 구독 메시지 전송: $msg")
                            }
                        }

                        override fun onMessage(ws: WebSocket, text: String) {
                            Log.d("WebSocket", "📩 수신 메시지: $text")
                            parseAndEmit(text)
                        }

                        override fun onClosing(ws: WebSocket, code: Int, reason: String) {
                            ws.close(1000, null)
                            webSocket = null
                            Log.d("WebSocket", "🔒 연결 종료 중: $reason")
                        }

                        override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                            webSocket = null
                            Log.e("WebSocket", "❌ 오류: ${t.message}", t)
                        }
                    })
                }
                .onFailure {
                    Log.e("WebSocket", "❌ 승인 키 획득 실패", it)
                }
        }
    }

    override fun disconnect() {
        webSocket?.close(1000, "Client disconnect")
        webSocket = null
        _stockTickerListFlow.value = emptyList()
    }

    private fun createSubscribeMessage(
        approvalKey: String,
        stockCode: String
    ): String {
        return JSONObject().apply {
            put("header", JSONObject().apply {
                put("approval_key", approvalKey)
                put("custtype", "P")
                put("tr_type", "1")
                put("content-type", "utf-8")
            })
            put("body", JSONObject().apply {
                put("input", JSONObject().apply {
                    put("tr_id", "H0STCNT0")
                    put("tr_key", stockCode)
                })
            })
        }.toString()
    }

    private fun parseAndEmit(message: String) {
        if (!message.startsWith("0|")) return

        val parts = message.split("|")
        val count = parts.getOrNull(2)?.toIntOrNull() ?: return
        val payload = parts.getOrNull(3) ?: return

        val payloadParts = payload.split("^")

        val parsedList = mutableListOf<StockPriceSummary>()

        repeat(count) { i ->
            val start = i * StockPriceSummary.STOCK_PRICE_FIELDS_COUNT
            val end = start + StockPriceSummary.STOCK_PRICE_FIELDS_COUNT
            if (end <= payloadParts.size) {
                val record = payloadParts.subList(start, end).joinToString("^")
                StockPriceSummary.parseStockPriceSummary(record)?.let { parsed ->
                    parsedList.add(parsed)
                }
            }
        }

        _stockTickerListFlow.update { currentList ->
            val mutableList = currentList.toMutableList()
            parsedList.forEach { parsed ->
                val index = mutableList.indexOfFirst { it.stockCode == parsed.stockCode }
                if (index >= 0) {
                    mutableList[index] = parsed
                } else {
                    mutableList.add(parsed)
                }
            }
            mutableList.toList()
        }

        if (parsedList.isNotEmpty()) {
            val codes = parsedList.joinToString(", ") { it.stockCode }
            Log.d("WebSocket", "📈 ${parsedList.size}건 업데이트 완료 → [$codes]")
        }
    }
}
