package com.jusicool.repository.order

import com.jusicool.entity.order.BuyRequestModel
import com.jusicool.entity.order.BuyReserveModel
import com.jusicool.entity.order.BuyResponseModel
import com.jusicool.entity.order.OrderModel
import com.jusicool.entity.order.SellRequestModel
import com.jusicool.entity.order.SellReserveModel
import com.jusicool.entity.order.SellResponseModel
import com.jusicool.model.mapper.order.toDto
import com.jusicool.entity.orderHistory.OrderHistory
import com.jusicool.model.mapper.order.toEntity
import com.jusicool.model.mapper.order.toModel
import com.jusicool.network.datasource.order.OrderDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : OrderRepository {
    override fun getMonthOrder(): Flow<OrderModel> {
        return orderDataSource.getMonthOrder().map { it.toModel() }
    }

    override fun postBuy(marketCode: String, quantity: BuyRequestModel): Flow<BuyResponseModel> {
        return orderDataSource.postBuy(marketCode = marketCode, quantity = quantity.toDto()).map { it.toModel() }
    }

    override fun postSell(marketCode: String, quantity: SellRequestModel): Flow<SellResponseModel> {
        return orderDataSource.postSell(marketCode = marketCode, quantity = quantity.toDto()).map { it.toModel() }
    }

    override fun postReserveBuy(marketCode: String, body: BuyReserveModel): Flow<Unit> {
        return orderDataSource.postReserveBuy(marketCode = marketCode, body = body.toDto())
    }

    override fun postReserveSell(marketCode: String, body: SellReserveModel): Flow<Unit> {
        return orderDataSource.postReserveSell(marketCode = marketCode, body = body.toDto())
    }

    override fun getOrderHistory(type: String): Flow<List<OrderHistory>> {
        return orderDataSource.getOrderHistory(type).map { list -> list.map { it.toEntity() } }
    }
}