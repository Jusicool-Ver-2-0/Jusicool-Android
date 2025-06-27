package com.jusicool.repository.holding

import com.jusicool.entity.holding.HoldingModel
import com.jusicool.local.dao.HoldingDao
import com.jusicool.local.dao.MarketDao
import com.jusicool.local.entity.HoldingEntity
import com.jusicool.local.entity.MarketEntity
import com.jusicool.local.util.mapper.toDomain.toModel
import com.jusicool.local.util.mapper.toLocal.toEntities
import com.jusicool.network.datasource.holding.HoldingDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HoldingRepositoryImpl @Inject constructor(
    private val holdingDataSource: HoldingDataSource,
    private val holdingDao: HoldingDao,
    private val marketDao: MarketDao,
) : HoldingRepository {

    // DB에서 홀딩 데이터를 관찰하고 도메인 모델로 변환해 반환
    override fun observeHoldings(): Flow<List<HoldingModel>> =
        holdingDao.observeAllHoldingsWithMarket()
            .map { list -> list.map { it.toModel() } }

    // 네트워크에서 홀딩 데이터를 받아와 DB를 갱신하는 함수
    override suspend fun refreshHoldings() = withContext(Dispatchers.IO) {
        holdingDataSource.getHolding()
            .collect { holdingResponseList ->

                // 현재 DB에 저장된 홀딩 리스트 조회
                val localHoldings = holdingDao.getAllHoldings()

                // 새로 받아온 홀딩과 마켓 데이터를 담을 리스트 초기화
                val newHoldings = mutableListOf<HoldingEntity>()
                val newMarkets = mutableListOf<MarketEntity>()

                // 응답 리스트를 순회하며 로컬 엔티티로 변환 후 리스트에 추가
                holdingResponseList.forEach { holdingResponse ->
                    val (holdingEntity, marketEntity) = holdingResponse.toEntities()
                    newHoldings.add(holdingEntity)
                    newMarkets.add(marketEntity)
                }

                // DB에 이미 존재하는 마켓 ID 집합 생성
                val localMarketIds = localHoldings.map { it.marketId }.toSet()

                // 새 마켓 리스트 중 DB에 없는 마켓만 필터링
                val insertMarkets = newMarkets.filter { it.id !in localMarketIds }

                // 새 홀딩과 기존 홀딩 각각의 ID 리스트 생성
                val newIds = newHoldings.map { it.id }
                val localIds = localHoldings.map { it.id }

                // 기존 DB에서 새 데이터에 없는 ID만 골라 삭제 대상 리스트 생성
                val deleteIds = localIds.filterNot { it in newIds }

                // 홀딩과 마켓 데이터를 업데이트 및 삭제하는 DAO 함수 호출
                holdingDao.updateHoldings(
                    newHoldings = newHoldings,
                    deleteIds = deleteIds,
                    newMarkets = insertMarkets,
                    marketDao = marketDao
                )
            }
    }
}

