package com.jusicool.network.datasource.koreaInvestment.ws

import android.util.Log
import com.jusicool.model.koreaInvestment.ws.StockPriceSummary
import com.jusicool.network.util.KoreaInvestmentAuthManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _stockTickerFlow = MutableStateFlow<StockPriceSummary?>(null)
    override val stockTickerFlow: StateFlow<StockPriceSummary?> = _stockTickerFlow.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun connect(stockCode: String) {
        if (webSocket != null) {
            disconnect()
        }

        scope.launch {
            runCatching {
                authManager.getApprovalKey()
            }.onSuccess { approvalKey ->
                val request = Request.Builder()
                    .url("ws://ops.koreainvestment.com:21000")
                    .build()

                webSocket = client.newWebSocket(request, object : WebSocketListener() {
                    override fun onOpen(ws: WebSocket, response: Response) {
                        val msg = createSubscribeMessage(approvalKey, stockCode)
                        ws.send(msg)
                        Log.d("WebSocket", "✅ 구독 메시지 전송: $msg")
                    }

                    override fun onMessage(ws: WebSocket, text: String) {
                        Log.d("WebSocket", "📩 수신 메시지: $text")
                        Log.d("WebSocket", "\uD83D\uDD12 파싱된 페이지: ${StockPriceSummary.fromRawData(text)}")
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
            }.onFailure {
                Log.e("WebSocket", "❌ 승인 키 획득 실패", it)
            }
        }
    }

    override fun disconnect() {
        webSocket?.close(1000, "Client disconnect")
        webSocket = null
    }

    private fun createSubscribeMessage(
        approvalKey: String,
        stockCode: String
    ): String {
        val json = JSONObject().apply {
            put("header", JSONObject().apply {
                put("approval_key", approvalKey)
                put("custtype", "P")
                put("tr_type", "1")
                put("content-type", "utf-8")
            })
            put("body", JSONObject().apply {
                put("input", JSONObject().apply {
                    put("tr_id", "H0STASP0")
                    put("tr_key", stockCode)
                })
            })
        }
        return json.toString()
    }

    private fun parseAndEmit(message: String) {
        // 실시간 데이터 메시지는 '^' 구분된 문자열
        val parsed = StockPriceSummary.fromRawData(message)
        if (parsed != null) {
            scope.launch {
                _stockTickerFlow.emit(parsed)
            }
        }
    }
}
