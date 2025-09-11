package com.jusicool.model.mapper.crypto


import com.jusicool.entity.crypto.UpbitTickerModel
import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.model.crypto.ws.UpbitTickerResponse

fun UpbitTickerResponse.toModel(): UpbitTickerModel =
    UpbitTickerModel(
        market = market,
        price = tradePrice,
        changeSign = change,
        changePrice = signedChangePrice,
        changeRatePercent = signedChangeRate * 100.0,
        ts = tradeTimestamp
    )


fun UpbitTickerModel.toEntity(): AssetsCurrentPrice =
    AssetsCurrentPrice(
        market = market,
        currentPrice = price,
        priceDifference = changePrice,
        priceDifferenceRate = changeRatePercent
    )


fun UpbitTickerResponse.toEntity(): AssetsCurrentPrice = this.toModel().toEntity()