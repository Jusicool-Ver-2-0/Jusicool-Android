package com.jusicool.network.util

import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.concurrent.ConcurrentHashMap

class BasicCookieJar : CookieJar {

    private val cookieStore = ConcurrentHashMap<String, MutableList<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val validCookies = cookies.filter { it.expiresAt >= System.currentTimeMillis() }
        cookieStore[url.host] = validCookies.toMutableList()

        if (/*BuildConfig.DEBUG*/ true) {
            Log.d("SimpleCookieJar", "Saved cookies for ${url.host}: $validCookies")
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookies = cookieStore[url.host]?.filter { it.expiresAt >= System.currentTimeMillis() }
            ?.also { cookieStore[url.host] = it.toMutableList() } // 만료된 쿠키 제거

        if (/*BuildConfig.DEBUG*/ true) {
            Log.d("SimpleCookieJar", "Loaded cookies for ${url.host}: $cookies")
        }

        return cookies ?: emptyList()
    }
}
