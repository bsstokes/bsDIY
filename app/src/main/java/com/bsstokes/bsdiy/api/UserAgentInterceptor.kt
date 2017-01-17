package com.bsstokes.bsdiy.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UserAgentInterceptor(private val userAgent: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .header("User-Agent", userAgent)
                .build()
        return chain.proceed(request)
    }
}
