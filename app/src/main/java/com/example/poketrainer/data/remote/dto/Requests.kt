package com.example.poketrainer.data.remote.dto

data class LoginRequest(
    val email: String,
    val password: String,
    val device_name: String = "android",
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String,
    val region: String = "Kanto",
    val device_name: String = "android",
)

// En PATCH, Gson omite los campos null: solo se envía lo que cambió.
data class UpdateTrainerRequest(val name: String? = null, val email: String? = null, val region: String? = null)
data class StorePokemonRequest(val nombre: String, val tipo: String, val nivel: Int = 1)
data class UpdatePokemonRequest(val nombre: String? = null, val tipo: String? = null, val nivel: Int? = null)
