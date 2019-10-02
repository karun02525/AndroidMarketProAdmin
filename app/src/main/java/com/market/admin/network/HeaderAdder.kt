package com.market.admin.network

import com.market.admin.utils.SharedPref
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


//This interceptor adds auth token to all request dynamically
class HeaderAdder : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Authorization",SharedPref.instance.authToken!!).build()
        return chain.proceed(request)
    }
}
