package com.jusicool.network.util

import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class KoreaInvestmentAuthenticator(
    private val koreaInvestmentAuthManager: KoreaInvestmentAuthManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) {
            return null
        }

        val token = try {
            runBlocking {
                koreaInvestmentAuthManager.getValidAccessToken(forceRefresh = true)
            }
        } catch (e: Exception) {
            Log.e("KoreaInvestmentAuth", "Token refresh failed", e)
            null
        }

        return token?.let {
            response.request.newBuilder()
                .header("Authorization", "Bearer $it")
                .build()
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
