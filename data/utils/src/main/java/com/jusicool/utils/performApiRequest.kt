package com.jusicool.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <reified T> performApiRequest(crossinline apiCall: suspend () -> T): Flow<T> = flow {
    emit(
        JusicoolApiHandler<T>()
            .httpRequest{ apiCall() }
            .sendRequest()
    )
}.flowOn(Dispatchers.IO)