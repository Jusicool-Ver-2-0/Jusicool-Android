package com.jusicool.network.util

import android.content.SharedPreferences
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.concurrent.ConcurrentHashMap

class BasicCookieJar(
    private val prefs: SharedPreferences
) : CookieJar {

    private val cookieStore = ConcurrentHashMap<String, MutableList<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val now = System.currentTimeMillis()
        val validCookies = cookies.filter { it.expiresAt >= now }
        cookieStore[url.host] = validCookies.toMutableList()
        val serialized = validCookies.map { serializeCookie(it) }.toSet()
        prefs.edit().putStringSet(url.host, serialized).apply()
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        if (!cookieStore.containsKey(url.host)) {
            val serialized = prefs.getStringSet(url.host, emptySet()) ?: emptySet()
            val loadedCookies = serialized.mapNotNull { deserializeCookie(it) }.toMutableList()
            cookieStore[url.host] = loadedCookies
        }

        val now = System.currentTimeMillis()
        val allCookies = cookieStore[url.host]
        val filtered = allCookies?.filter { it.expiresAt >= now }?.toMutableList() ?: mutableListOf()
        cookieStore[url.host] = filtered
        return filtered
    }

    fun hasAuthCookies(host: String): Boolean {
        val now = System.currentTimeMillis()
        val cookies = cookieStore[host] ?: run {
            val serialized = prefs.getStringSet(host, emptySet()) ?: emptySet()
            val loadedCookies = serialized.mapNotNull { deserializeCookie(it) }
            cookieStore[host] = loadedCookies.toMutableList()
            loadedCookies
        }

        val hasCsrf = cookies.any { it.name == "csrftoken" && it.expiresAt >= now }
        val hasSession = cookies.any { it.name == "sessionid" && it.expiresAt >= now }
        return hasCsrf && hasSession
    }

    private fun serializeCookie(cookie: Cookie): String {
        val builder = StringBuilder()
        builder.append("${cookie.name}=${cookie.value}")
        builder.append(";expiresAt=${cookie.expiresAt}")
        builder.append(";path=${cookie.path}")
        builder.append(";domain=${cookie.domain}")
        if (cookie.secure) builder.append(";secure")
        if (cookie.httpOnly) builder.append(";httpOnly")
        return builder.toString()
    }

    private fun deserializeCookie(cookieString: String): Cookie? {
        return try {
            val parts = cookieString.split(";")
            val nameValue = parts[0].split("=")
            val name = nameValue[0]
            val value = nameValue.getOrNull(1) ?: ""
            var expiresAt = Long.MAX_VALUE
            var path = "/"
            var domain = ""
            var secure = false
            var httpOnly = false

            for (i in 1 until parts.size) {
                val part = parts[i]
                when {
                    part.startsWith("expiresAt=") -> expiresAt = part.removePrefix("expiresAt=").toLong()
                    part.startsWith("path=") -> path = part.removePrefix("path=")
                    part.startsWith("domain=") -> domain = part.removePrefix("domain=")
                    part == "secure" -> secure = true
                    part == "httpOnly" -> httpOnly = true
                }
            }

            Cookie.Builder()
                .name(name)
                .value(value)
                .expiresAt(expiresAt)
                .path(path)
                .domain(domain)
                .apply {
                    if (secure) secure()
                    if (httpOnly) httpOnly()
                }
                .build()
        } catch (e: Exception) {
            null
        }
    }
}
