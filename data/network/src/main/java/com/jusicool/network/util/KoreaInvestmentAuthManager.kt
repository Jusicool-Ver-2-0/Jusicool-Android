package com.jusicool.network.util

import android.content.Context
import android.util.Log
import com.jusicool.model.koreaInvestment.AccessKeyRequest
import com.jusicool.model.koreaInvestment.AccessTokenRequest
import com.jusicool.network.BuildConfig
import com.jusicool.network.api.KoreaInvestmentApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KoreaInvestmentAuthManager @Inject constructor(
    private val koreaInvestmentApi: KoreaInvestmentApi,
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private val tokenMutex = Mutex()
    private val approvalKeyMutex = Mutex()

    suspend fun getValidAccessToken(): String {
        return tokenMutex.withLock {
            try {
                val now = System.currentTimeMillis()

                val storedToken = prefs.getString("access_token", null)
                val storedExpires = prefs.getLong("access_token_expires_at", 0L)

                if (storedToken != null && now < storedExpires) {
                    Log.d("AuthManager", "✅ SharedPreferences 캐시된 AccessToken 사용: $storedToken")
                    return storedToken
                }

                Log.d("AuthManager", "🔄 API 요청으로 AccessToken 갱신")
                val response = koreaInvestmentApi.getAccessToken(
                    AccessTokenRequest(
                        grantType = "client_credentials",
                        appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
                        secretKey = BuildConfig.KOREAINVESTMENT_APP_SECRET,
                    )
                )

                val newToken = response.accessToken
                val expiresAt = (now + (response.expiresIn * 1000L) - 5 * 60 * 1000L).toLong()

                prefs.edit().apply {
                    putString("access_token", newToken)
                    putLong("access_token_expires_at", expiresAt)
                }.apply()

                Log.d("AuthManager", "✅ 새 AccessToken 저장 및 반환: $newToken, 만료시간: $expiresAt")
                return newToken
            } catch (e: Exception) {
                Log.e("AuthManager", "❌ AccessToken 갱신 실패: ${e.message}", e)

                // 기존 저장된 토큰이라도 있으면 반환
                val fallbackToken = prefs.getString("access_token", null)
                if (fallbackToken != null) {
                    Log.w("AuthManager", "⚠️ API 실패, 캐시된 AccessToken 반환: $fallbackToken")
                    return fallbackToken
                }

                throw e // 진짜로 토큰이 없으면 예외 던짐
            }
        }
    }

    suspend fun getApprovalKey(): String {
        return approvalKeyMutex.withLock {
            try {
                val now = System.currentTimeMillis()

                val storedKey = prefs.getString("approval_key", null)
                val storedExpires = prefs.getLong("approval_key_expires_at", 0L)

                if (storedKey != null && now < storedExpires) {
                    Log.d("AuthManager", "✅ SharedPreferences 캐시된 ApprovalKey 사용: $storedKey")
                    return storedKey
                }

                Log.d("AuthManager", "🔄 API 요청으로 ApprovalKey 갱신")
                val response = koreaInvestmentApi.getWebSocketAccessToken(
                    AccessKeyRequest(
                        grantType = "client_credentials",
                        appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
                        secretKey = BuildConfig.KOREAINVESTMENT_APP_SECRET,
                    )
                )

                val newKey = response.approvalKey
                val expiresAt = now + 24 * 60 * 60 * 1000L

                prefs.edit().apply {
                    putString("approval_key", newKey)
                    putLong("approval_key_expires_at", expiresAt)
                }.apply()

                Log.d("AuthManager", "✅ 새 ApprovalKey 저장 및 반환: $newKey, 만료시간: $expiresAt")
                return newKey
            } catch (e: Exception) {
                Log.e("AuthManager", "❌ ApprovalKey 갱신 실패: ${e.message}", e)

                // 기존 저장된 키라도 있으면 반환
                val fallbackKey = prefs.getString("approval_key", null)
                if (fallbackKey != null) {
                    Log.w("AuthManager", "⚠️ API 실패, 캐시된 ApprovalKey 반환: $fallbackKey")
                    return fallbackKey
                }

                throw e // 진짜로 키가 없으면 예외 던짐
            }
        }
    }
}
