package com.example.poketrainer.data.remote

import com.example.poketrainer.data.local.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // OkHttp ejecuta el interceptor en un hilo de fondo, por eso runBlocking es aceptable aquí.
        val token = runBlocking { tokenManager.token.first() }
        val request = chain.request().newBuilder()
            .header("Accept", "application/json")
            .apply { if (token != null) header("Authorization", "Bearer $token") }
            .build()
        return chain.proceed(request)
    }
}
