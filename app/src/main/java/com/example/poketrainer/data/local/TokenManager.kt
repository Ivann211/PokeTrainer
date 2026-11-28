package com.example.poketrainer.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session")

class TokenManager(private val context: Context) {
    private val TOKEN = stringPreferencesKey("access_token")
    private val TRAINER_ID = intPreferencesKey("trainer_id")

    val token: Flow<String?> = context.dataStore.data.map { it[TOKEN] }
    val trainerId: Flow<Int?> = context.dataStore.data.map { it[TRAINER_ID] }

    suspend fun save(token: String, trainerId: Int) {
        context.dataStore.edit {
            it[TOKEN] = token
            it[TRAINER_ID] = trainerId
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
//No Changes