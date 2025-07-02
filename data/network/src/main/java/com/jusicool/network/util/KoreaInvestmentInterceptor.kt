package com.jusicool.network.util

import android.util.Log
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class KoreaInvestmentInterceptor(
    private val koreaInvestmentAuthManager: Lazy<KoreaInvestmentAuthManager>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val urlPath = originalRequest.url.encodedPath

        // 🔐 토큰 발급용 요청은 Authorization 붙이지 않고 그대로 진행
        if (urlPath.contains("/oauth2")) {
            return chain.proceed(originalRequest)
        }

        val token = runBlocking {
            koreaInvestmentAuthManager.get().getValidAccessToken(forceRefresh = false)
        }

        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        var response = chain.proceed(authenticatedRequest)

        if (response.code == 401 || response.code == 500) {
            Log.w("KoreaInvestmentInterceptor", "Response ${response.code} → retrying after token refresh")
            response.close()

            val newToken = runBlocking {
                koreaInvestmentAuthManager.get().getValidAccessToken(forceRefresh = true)
            }

            val retriedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()

            response = chain.proceed(retriedRequest)
        }

        return response
    }
}
