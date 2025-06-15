package com.jusicool.network.util

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class KoreaInvestmentAuthInterceptor(
    private val koreaInvestmentAuthManager: KoreaInvestmentAuthManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val needsAuth = request.url.encodedPath.startsWith("/uapi/")

        val newRequest = if (needsAuth) {
            val token = runBlocking { koreaInvestmentAuthManager.getValidAccessToken() }
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }

        return chain.proceed(newRequest)
    }
}
