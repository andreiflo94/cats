package com.heixss.cats.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeadersInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "e3165119-d17a-4cef-98ad-e594f9b1a84f")
            .build()
        return chain.proceed(request)
    }
}