package com.example.poketrainer.data.remote.dto

data class TrainerDto (
    val id: Int,
    val name: String,
    val email: String,
    val region: String,
    val pokemon_count: Int? = null,
    val pokemon: List<PokemonDto>? = null
)

data class PokemonDto (
    val id: Int,
    val user_id: Int,
    val nombre: String,
    val tipo: String,
    val nivel: Int,
    val sprite_url: String?,
    val trainer: TrainerDto? = null
) {
    val tipos: List<String> get() = tipo.split(",").map { it.trim() }.filter { it.isNotEmpty() }
}

data class AuthResponse(
    val trainer: TrainerDto,
    val access_token: String,
    val token_type: String,
)

data class DataResponse<T>(val data: T)

data class PageResponse<T>(val data: List<T>, val meta: PageMeta)

data class PageMeta(
    val current_page: Int,
    val last_page: Int,
    val total: Int,
)
data class MessageResponse(val message: String)

data class ApiErrorResponse(val message: String, val errors: Map<String, List<String>>? = null)
