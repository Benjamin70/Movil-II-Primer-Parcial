package com.example.android.dessertpusher

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import com.example.android.dessertpusher.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity(), LifecycleObserver {

    private var revenue = 0
    private var dessertsSold = 0

    // Timer para contar segundos
    private lateinit var dessertTimer: DessertTimer

    // Contains all the views
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val KEY_REVENUE = "key_revenue"
        private const val KEY_DESSERTS_SOLD = "key_desserts_sold"
        private const val KEY_TIMER_SECONDS = "key_timer_seconds"
    }

    /** Dessert Data **/
    data class Dessert(val imageId: Int, val price: Int, val startProductionAmount: Int)

    private val allDesserts = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.gingerbread, 50, 100),
        Dessert(R.drawable.honeycomb, 100, 200),
        Dessert(R.drawable.icecreamsandwich, 500, 500),
        Dessert(R.drawable.jellybean, 1000, 1000),
        Dessert(R.drawable.kitkat, 2000, 2000),
        Dessert(R.drawable.lollipop, 3000, 4000),
        Dessert(R.drawable.marshmallow, 4000, 8000),
        Dessert(R.drawable.nougat, 5000, 16000),
        Dessert(R.drawable.oreo, 6000, 20000)
    )
    private var currentDessert = allDesserts[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Log con Timber
        Timber.i("👉 onCreate ejecutado | instancia=${this.hashCode()}")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.dessertButton.setOnClickListener {
            onDessertClicked()
        }

        // Restaurar datos si hay estado guardado
        if (savedInstanceState != null) {
            revenue = savedInstanceState.getInt(KEY_REVENUE, 0)
            dessertsSold = savedInstanceState.getInt(KEY_DESSERTS_SOLD, 0)
            val timerSeconds = savedInstanceState.getInt(KEY_TIMER_SECONDS, 0)

            Timber.i("♻️ Estado restaurado -> revenue=$revenue, sold=$dessertsSold, seconds=$timerSeconds")
        }

        binding.revenue = revenue
        binding.amountSold = dessertsSold
        binding.dessertButton.setImageResource(currentDessert.imageId)

        // Inicializamos el temporizador con el ciclo de vida de esta actividad
        dessertTimer = DessertTimer(this.lifecycle)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_REVENUE, revenue)
        outState.putInt(KEY_DESSERTS_SOLD, dessertsSold)
        outState.putInt(KEY_TIMER_SECONDS, dessertTimer.secondsCount)

        Timber.i("💾 Estado guardado -> revenue=$revenue, sold=$dessertsSold, seconds=${dessertTimer.secondsCount}")
    }

    override fun onStart() {
        super.onStart()
        Timber.i("🚀 onStart corriendo... todo listo para vender postres 🍩")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("▶️ onResume — la app vuelve a estar activa")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("⏸️ onPause — usuario deja la app temporalmente")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("🛑 onStop — la UI ya no es visible")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("🔄 onRestart — volviendo después de estar parada")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("💀 onDestroy — MainActivity destruida")
    }

    /**
     * Updates the score when the dessert is clicked. Possibly shows a new dessert.
     */
    private fun onDessertClicked() {
        revenue += currentDessert.price
        dessertsSold++

        binding.revenue = revenue
        binding.amountSold = dessertsSold

        showCurrentDessert()
    }

    private fun showCurrentDessert() {
        var newDessert = allDesserts[0]
        for (dessert in allDesserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                newDessert = dessert
            } else break
        }
        if (newDessert != currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(newDessert.imageId)
        }
    }

    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.share_text, dessertsSold, revenue))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this,
                getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }
}
