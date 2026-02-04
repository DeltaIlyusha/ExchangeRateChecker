package com.iliaziuzin.exchangeratechecker.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("apikey", "$apiKey")
            .build()
        return chain.proceed(newRequest)
    }
}
