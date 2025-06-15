package com.jusicool.network.util

import android.content.Context
import com.jusicool.network.api.KoreaInvestmentApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KoreaInvestmentAuthManager @Inject constructor(
    private val koreaInvestmentApi: KoreaInvestmentApi, // 토큰 발급용 API
    private val context: Context
) {

    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private var accessToken: String? = null
    private var expiresAt: Long = 0L

    suspend fun getValidAccessToken(): String {
        val now = System.currentTimeMillis()
        if (accessToken != null && now < expiresAt) return accessToken!!

        val storedToken = prefs.getString("access_token", null)
        val storedExpires = prefs.getLong("expires_at", 0L)
        if (storedToken != null && now < storedExpires) {
            accessToken = storedToken
            expiresAt = storedExpires
            return accessToken!!
        }

        // 🔐 발급 요청
        val response = koreaInvestmentApi.getAccessToken()

        accessToken = response.accessToken
        expiresAt = (now + (response.expiresIn * 1000L) - 5 * 60 * 1000L).toLong() // 만료 5분 전 갱신

        prefs.edit().apply {
            putString("access_token", accessToken)
            putLong("expires_at", expiresAt)
        }.apply()

        return accessToken!!
    }
}
