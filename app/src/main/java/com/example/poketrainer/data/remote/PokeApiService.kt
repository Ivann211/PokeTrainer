package com.example.poketrainer.data.remote

import com.example.poketrainer.data.remote.dto.AuthResponse
import com.example.poketrainer.data.remote.dto.DataResponse
import com.example.poketrainer.data.remote.dto.LoginRequest
import com.example.poketrainer.data.remote.dto.MessageResponse
import com.example.poketrainer.data.remote.dto.PageResponse
import com.example.poketrainer.data.remote.dto.PokemonDto
import com.example.poketrainer.data.remote.dto.RegisterRequest
import com.example.poketrainer.data.remote.dto.StorePokemonRequest
import com.example.poketrainer.data.remote.dto.TrainerDto
import com.example.poketrainer.data.remote.dto.UpdatePokemonRequest
import com.example.poketrainer.data.remote.dto.UpdateTrainerRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @POST("register")
    suspend fun register(@Body body: RegisterRequest): AuthResponse

    @POST("login")
    suspend fun login(@Body body: LoginRequest): AuthResponse

    @POST("logout")
    suspend fun logout(): MessageResponse

    // ---- Entrenadores ----
    @GET("trainers")
    suspend fun getTrainers(@Query("page") page: Int = 1): PageResponse<TrainerDto>

    @GET("trainers/{id}")
    suspend fun getTrainer(@Path("id") id: Int): DataResponse<TrainerDto>

    @PATCH("trainers/{id}")
    suspend fun updateTrainer(@Path("id") id: Int, @Body body: UpdateTrainerRequest): DataResponse<TrainerDto>

    @DELETE("trainers/{id}")
    suspend fun deleteTrainer(@Path("id") id: Int): MessageResponse

    // ---- Pokémon ----
    @GET("pokemons")
    suspend fun getMyPokemon(@Query("page") page: Int = 1): PageResponse<PokemonDto>

    @POST("pokemons")
    suspend fun createPokemon(@Body body: StorePokemonRequest): DataResponse<PokemonDto>

    @GET("pokemons/{id}")
    suspend fun getPokemon(@Path("id") id: Int): DataResponse<PokemonDto>

    @PATCH("pokemons/{id}")
    suspend fun updatePokemon(@Path("id") id: Int, @Body body: UpdatePokemonRequest): DataResponse<PokemonDto>

    @DELETE("pokemons/{id}")
    suspend fun deletePokemon(@Path("id") id: Int): MessageResponse
}
