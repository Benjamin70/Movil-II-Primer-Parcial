package com.example.android.dessertpusher

import android.app.Application
import timber.log.Timber

class PusherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Inicializamos Timber con el DebugTree
        Timber.plant(Timber.DebugTree())
        Timber.i("🌲 Timber inicializado en PusherApplication")
    }
}
