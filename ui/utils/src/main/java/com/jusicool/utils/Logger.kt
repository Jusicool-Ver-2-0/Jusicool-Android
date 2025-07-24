package com.jusicool.utils

import android.util.Log
import com.jusicool.uiutils.BuildConfig

object Logger {
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            val location = getCallerLocation()
            Log.d(tag, "[$location] → $message")
        }
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            val location = getCallerLocation()
            Log.e(tag, "[$location] → $message", throwable)
        }
    }

    private fun getCallerLocation(): String {
        val stackTrace = Thread.currentThread().stackTrace

        val caller = stackTrace
            .dropWhile { frame ->
                frame.className.startsWith("java.") ||
                        frame.className.startsWith("kotlin.") ||
                        frame.className.startsWith("dalvik.") ||
                        frame.className.contains("Logger")
            }
            .firstOrNull()

        return caller?.let {
            "${it.fileName}:${it.lineNumber} (${it.className}.${it.methodName})"
        } ?: "Unknown Source"
    }
}
