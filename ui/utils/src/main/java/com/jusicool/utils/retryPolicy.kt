package com.jusicool.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector

fun retryPolicy(logKey: String): suspend FlowCollector<*>.(Throwable, Long) -> Boolean = { cause, attempt ->
    Logger.e(logKey, "Retry attempt $attempt due to $cause")
    if (attempt < 3) {
        delay(3000)
        true
    } else {
        false
    }
}
