package com.jusicool.model.mapper.crypto

import com.jusicool.entity.crypto.DayCandleModel
import com.jusicool.model.crypto.DayCandleResponse

fun DayCandleResponse.toModel(): DayCandleModel =
    DayCandleModel(
        changePrice = this.changePrice
    )