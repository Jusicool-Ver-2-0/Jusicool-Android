package com.jusicool.model.mapper.crypto

import com.jusicool.entity.crypto.CurrentCryptoPriceModel
import com.jusicool.model.crypto.CurrentCryptoPriceResponse

fun CurrentCryptoPriceResponse.toModel(): CurrentCryptoPriceModel =
    CurrentCryptoPriceModel(
        market = this.market,
        tradePrice = this.tradePrice
    )