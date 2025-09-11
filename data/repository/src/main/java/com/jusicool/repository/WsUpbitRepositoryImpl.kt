package com.jusicool.repository

import android.util.Log
import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.model.crypto.CurrentCryptoPriceResponse
import com.jusicool.model.crypto.ws.UpbitTickerResponse
import com.jusicool.model.mapper.crypto.toEntity
import com.jusicool.network.datasource.crypto.ws.UpbitWebSocketManagerInterface
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class WsUpbitRepositoryImpl @Inject constructor(
    private val wsManager: UpbitWebSocketManagerInterface
) : WsUpbitRepository {

    override fun observeTicker(markets: List<String>): Flow<List<AssetsCurrentPrice>> =
        callbackFlow {
            wsManager.connect(markets)

            val job = launch {
                wsManager.tickerListFlow
                    .map { list -> list.map(UpbitTickerResponse::toEntity) }
                    .collect { data ->
                        val result = trySend(data)
                        if (result.isFailure) {
                            Log.d("WsUpbitRepository", "Flow send 실패: $result")
                        }
                    }
            }

            awaitClose {
                wsManager.disconnect()
                job.cancel()
                Log.d("WsUpbitRepository", "Flow 종료")
            }
        }
}
