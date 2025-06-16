package com.jusicool.network.util

import android.content.Context
import com.jusicool.model.koreaInvestment.AccessKeyRequest
import com.jusicool.network.BuildConfig
import com.jusicool.network.api.KoreaInvestmentApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KoreaInvestmentAuthManager @Inject constructor(
    private val koreaInvestmentApi: KoreaInvestmentApi,
    context: Context
) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private var accessToken: String? = null
    private var expiresAt: Long = 0L

    suspend fun getValidAccessToken(forceRefresh: Boolean = false): String {
        val now = System.currentTimeMillis()

        if (
            !forceRefresh
            && accessToken != null
            && now < expiresAt
        ) {
            return accessToken!!
        }

        val storedToken = prefs.getString("access_token", null)
        val storedExpires = prefs.getLong("expires_at", 0L)

        if (
            !forceRefresh
            && storedToken != null
            && now < storedExpires
        ) {
            accessToken = storedToken
            expiresAt = storedExpires
            return accessToken!!
        }

        val response = koreaInvestmentApi.getAccessToken(
            body = AccessKeyRequest(
                grantType = "client_credentials",
                appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
                secretKey = BuildConfig.KOREAINVESTMENT_APP_SECRET,
            )
        )

        accessToken = response.accessToken
        expiresAt = (now + (response.expiresIn * 1000L) - 5 * 60 * 1000L).toLong()

        prefs.edit().apply {
            putString("access_token", accessToken)
            putLong("expires_at", expiresAt)
        }.apply()

        return accessToken!!
    }
}
