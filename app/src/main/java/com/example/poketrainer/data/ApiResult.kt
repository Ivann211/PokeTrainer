package com.example.poketrainer.data

import com.example.poketrainer.data.remote.dto.ApiErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(
        val code: Int?,
        val message: String,
        val fieldErrors: Map<String, List<String>> = emptyMap(),
    ) : ApiResult<Nothing>
}

suspend fun <T> safeApiCall(call: suspend () -> T): ApiResult<T> = try {
    ApiResult.Success(call())
} catch (e: HttpException) {
    val body = e.response()?.errorBody()?.string()
    val parsed = runCatching { Gson().fromJson(body, ApiErrorResponse::class.java) }.getOrNull()
    val message = when (e.code()) {
        401 -> parsed?.message ?: "Sesión expirada, inicia sesión de nuevo"
        403 -> "No tienes permiso para esta acción"
        404 -> "Recurso no encontrado"
        422 -> parsed?.message ?: "Revisa los datos del formulario"
        429 -> "Demasiados intentos, espera un minuto"
        else -> "Error del servidor (" + e.code() + ")"
    }
    ApiResult.Error(e.code(), message, parsed?.errors.orEmpty())
} catch (e: IOException) {
    ApiResult.Error(null, "Sin conexión con el servidor. Revisa tu red o la URL.")
}
