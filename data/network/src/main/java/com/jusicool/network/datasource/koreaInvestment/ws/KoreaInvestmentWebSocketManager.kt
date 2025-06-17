package com.jusicool.network.datasource.koreaInvestment.ws

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
import javax.inject.Inject
import javax.inject.Named

class KoreaInvestmentWebSocketManager @Inject constructor(
    @Named("koreaInvestmentOkHttpClient") private val client: OkHttpClient,
    private val authManager: KoreaInvestmentAuthManager
) : KoreaInvestmentWebSocketManagerInterface {

    private var webSocket: WebSocket? = null

    private val _stockTickerFlow = MutableSharedFlow<StockPriceSummary>(replay = 1)
    override val stockTickerFlow: SharedFlow<StockPriceSummary> = _stockTickerFlow.asSharedFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun connect(trId: String, stockCode: String) {
        if (webSocket != null) {
            disconnect()
        }

        scope.launch {
            runCatching {
                authManager.getApprovalKey()
            }.onSuccess { approvalKey ->
                val request = Request.Builder()
                    .url("wss://realdata.koreainvestment.com:443/websocket")
                    .addHeader("Authorization", approvalKey)
                    .build()

                webSocket = client.newWebSocket(request, object : WebSocketListener() {
                    override fun onOpen(ws: WebSocket, response: Response) {
                        val msg = createSubscribeMessage(trId, stockCode)
                        ws.send(msg)
                    }

                    override fun onMessage(ws: WebSocket, text: String) {
                        parseAndEmit(text)
                    }

                    override fun onClosing(ws: WebSocket, code: Int, reason: String) {
                        ws.close(1000, null)
                        webSocket = null
                    }

                    override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                        webSocket = null
                        // TODO: 로그 및 재시도 처리
                    }
                })
            }.onFailure { e ->
                e.printStackTrace()
                // TODO: 오류 알림, 재시도, UI 표시 등
            }
        }
    }


    override fun disconnect() {
        webSocket?.close(1000, "Client disconnect")
        webSocket = null
    }

    private fun createSubscribeMessage(trId: String, stockCode: String): String {
        return """
            {
                "header": {
                    "tr_id": "$trId"
                },
                "body": {
                    "input": {
                        "marketDivCode": "001",
                        "stockCode": "$stockCode"
                    }
                }
            }
        """.trimIndent()
    }

    private fun parseAndEmit(message: String) {
        val parsed = StockPriceSummary.fromRawData(message)
        if (parsed != null) {
            scope.launch {
                _stockTickerFlow.emit(parsed)
            }
        }
    }
}
