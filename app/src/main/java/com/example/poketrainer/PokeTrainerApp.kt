package com.example.poketrainer

import android.app.Application
import com.example.poketrainer.di.AppContainer

class PokeTrainerApp : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
