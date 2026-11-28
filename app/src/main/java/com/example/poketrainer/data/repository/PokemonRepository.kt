package com.example.poketrainer.data.repository

import com.example.poketrainer.data.ApiResult
import com.example.poketrainer.data.remote.PokeApiService
import com.example.poketrainer.data.remote.dto.StorePokemonRequest
import com.example.poketrainer.data.remote.dto.UpdatePokemonRequest
import com.example.poketrainer.data.safeApiCall
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow


sealed interface PokemonChange {
    data object Saved : PokemonChange
    data class Released(val id: Int) : PokemonChange
}

class PokemonRepository(private val api: PokeApiService) {
    private val _changes = MutableSharedFlow<PokemonChange>(extraBufferCapacity = 1)
    val changes: SharedFlow<PokemonChange> = _changes.asSharedFlow()

    suspend fun getMyPokemon(page: Int = 1) = safeApiCall { api.getMyPokemon(page) }
    suspend fun getPokemon(id: Int) = safeApiCall { api.getPokemon(id).data }

    suspend fun create(body: StorePokemonRequest) = safeApiCall { api.createPokemon(body).data }
        .also { if (it is ApiResult.Success) _changes.tryEmit(PokemonChange.Saved) }

    suspend fun update(id: Int, body: UpdatePokemonRequest) = safeApiCall { api.updatePokemon(id, body).data }
        .also { if (it is ApiResult.Success) _changes.tryEmit(PokemonChange.Saved) }

    suspend fun delete(id: Int) = safeApiCall { api.deletePokemon(id) }
        .also { if (it is ApiResult.Success) _changes.tryEmit(PokemonChange.Released(id)) }
}
