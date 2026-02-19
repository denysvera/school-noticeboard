package com.nativkod.schoolnoticeboard.core.network

import com.nativkod.schoolnoticeboard.core.security.TokenStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenStore: TokenStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenStore.getToken()

        val request = if (!token.isNullOrBlank()) {
            chain.request().newBuilder()
                .header("X-D6-Auth", "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        return chain.proceed(request)
    }
}
