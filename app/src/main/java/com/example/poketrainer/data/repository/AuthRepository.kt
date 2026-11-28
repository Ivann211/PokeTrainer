package com.example.poketrainer.data.repository

import com.example.poketrainer.data.ApiResult
import com.example.poketrainer.data.local.TokenManager
import com.example.poketrainer.data.remote.PokeApiService
import com.example.poketrainer.data.remote.dto.LoginRequest
import com.example.poketrainer.data.remote.dto.RegisterRequest
import com.example.poketrainer.data.remote.dto.TrainerDto
import com.example.poketrainer.data.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepository(private val api: PokeApiService, private val tokenManager: TokenManager) {

    val isLoggedIn: Flow<Boolean> = tokenManager.token.map { it != null }

    suspend fun login(email: String, password: String): ApiResult<TrainerDto> {
        val result = safeApiCall { api.login(LoginRequest(email, password)) }
        if (result is ApiResult.Success) tokenManager.save(result.data.access_token, result.data.trainer.id)
        return when (result) {
            is ApiResult.Success -> ApiResult.Success(result.data.trainer)
            is ApiResult.Error -> result
        }
    }

    suspend fun register(request: RegisterRequest): ApiResult<TrainerDto> {
        val result = safeApiCall { api.register(request) }
        if (result is ApiResult.Success) tokenManager.save(result.data.access_token, result.data.trainer.id)
        return when (result) {
            is ApiResult.Success -> ApiResult.Success(result.data.trainer)
            is ApiResult.Error -> result
        }
    }

    suspend fun logout() {
        safeApiCall { api.logout() }
        tokenManager.clear()
    }
}
