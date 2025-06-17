package com.jusicool.network.util

import android.content.Context
import com.jusicool.model.koreaInvestment.AccessKeyRequest
import com.jusicool.network.BuildConfig
import com.jusicool.network.api.KoreaInvestmentApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KoreaInvestmentAuthManager @Inject constructor(
    private val koreaInvestmentApi: KoreaInvestmentApi,
    @ApplicationContext private val context: Context,
) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private var accessToken: String? = null
    private var accessTokenExpiresAt: Long = 0L

    private var approvalKey: String? = null
    private var approvalKeyExpiresAt: Long = 0L

    suspend fun getValidAccessToken(forceRefresh: Boolean = false): String {
        val now = System.currentTimeMillis()

        if (
            !forceRefresh &&
            accessToken != null &&
            now < accessTokenExpiresAt
        ) {
            return accessToken!!
        }

        val storedToken = prefs.getString("access_token", null)
        val storedExpires = prefs.getLong("access_token_expires_at", 0L)

        if (
            !forceRefresh &&
            storedToken != null &&
            now < storedExpires
        ) {
            accessToken = storedToken
            accessTokenExpiresAt = storedExpires
            return storedToken
        }

        val response = koreaInvestmentApi.getAccessToken(
            body = AccessKeyRequest(
                grantType = "client_credentials",
                appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
                secretKey = BuildConfig.KOREAINVESTMENT_APP_SECRET,
            )
        )

        accessToken = response.accessToken
        accessTokenExpiresAt = (now + (response.expiresIn * 1000L) - 5 * 60 * 1000L).toLong()

        prefs.edit().apply {
            putString("access_token", accessToken)
            putLong("access_token_expires_at", accessTokenExpiresAt)
        }.apply()

        return accessToken!!
    }

    suspend fun getApprovalKey(forceRefresh: Boolean = false): String {
        val now = System.currentTimeMillis()

        if (
            !forceRefresh &&
            approvalKey != null &&
            now < approvalKeyExpiresAt
        ) {
            return approvalKey!!
        }

        val storedKey = prefs.getString("approval_key", null)
        val storedExpires = prefs.getLong("approval_key_expires_at", 0L)

        if (
            !forceRefresh &&
            storedKey != null &&
            now < storedExpires
        ) {
            approvalKey = storedKey
            approvalKeyExpiresAt = storedExpires
            return storedKey
        }

        val response = koreaInvestmentApi.getWebSocketAccessToken(
            body = AccessKeyRequest(
                grantType = "client_credentials",
                appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
                secretKey = BuildConfig.KOREAINVESTMENT_APP_SECRET,
            )
        )

        approvalKey = response.approvalKey
        approvalKeyExpiresAt = now + (24 * 60 * 60 * 1000L)

        prefs.edit().apply {
            putString("approval_key", approvalKey)
            putLong("approval_key_expires_at", approvalKeyExpiresAt)
        }.apply()

        return approvalKey!!
    }
}
