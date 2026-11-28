package com.example.poketrainer.data.repository

import com.example.poketrainer.data.ApiResult
import com.example.poketrainer.data.remote.PokeApiService
import com.example.poketrainer.data.remote.dto.UpdateTrainerRequest
import com.example.poketrainer.data.safeApiCall
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed interface TrainerChange {
    data object Updated : TrainerChange
    data class Deleted(val id: Int) : TrainerChange
}

class TrainerRepository(private val api: PokeApiService){
    private var _changes = MutableSharedFlow<TrainerChange>(extraBufferCapacity = 1)
    val changes: SharedFlow<TrainerChange> = _changes.asSharedFlow()

    suspend fun getTrainers(page: Int = 1) = safeApiCall { api.getTrainers(page) }

    suspend fun getTrainer(id: Int) = safeApiCall { api.getTrainer(id) }

    suspend fun updateTrainer(id: Int, body: UpdateTrainerRequest) = safeApiCall {
        api.updateTrainer(id,body)
    }.also {
        if(it is ApiResult.Success){
            _changes.tryEmit(TrainerChange.Updated)
        }
    }

    suspend fun deleteTrainer(id: Int) = safeApiCall {
        api.deleteTrainer(id)
    }.also{
        if(it is ApiResult.Success){
            _changes.tryEmit(TrainerChange.Deleted(id))
        }
    }
}