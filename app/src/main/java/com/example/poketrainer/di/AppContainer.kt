package com.example.poketrainer.di

import android.content.Context
import com.example.poketrainer.data.local.TokenManager
import com.example.poketrainer.data.remote.NetworkModule
import com.example.poketrainer.data.repository.AuthRepository
import com.example.poketrainer.data.repository.PokemonRepository
import com.example.poketrainer.data.repository.TrainerRepository

class AppContainer(context: Context) {
    val tokenManager = TokenManager(context)
    private val api = NetworkModule.createApi(tokenManager)

    val authRepository = AuthRepository(api, tokenManager)
    val trainerRepository = TrainerRepository(api)
    val pokemonRepository = PokemonRepository(api)
}
