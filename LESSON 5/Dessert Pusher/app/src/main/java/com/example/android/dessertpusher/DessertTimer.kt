package com.example.android.dessertpusher

import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class DessertTimer(lifecycle: Lifecycle) : LifecycleObserver {

    var secondsCount = 0
    private var handler = Handler()
    private lateinit var runnable: Runnable

    init {
        // Se registra como observador de la Activity/Fragment
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startTimer() {
        runnable = Runnable {
            secondsCount++
            Timber.i("⏱️ Timer: $secondsCount segundos")
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopTimer() {
        handler.removeCallbacks(runnable)
        Timber.i("⏹️ Timer detenido en $secondsCount segundos")
    }
}
