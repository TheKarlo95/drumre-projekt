package hr.fer.drumre.rec

import android.os.Handler
import androidx.appcompat.widget.SearchView

class DebouncedOnQueryTextListener(
    private val delayMillis: Long = DEFAULT_DELAY,
    private val onChange: (query: String) -> Unit
) : SearchView.OnQueryTextListener {

    private val handler: Handler = Handler()
    private var runnable: Runnable? = null

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean =
        if (newText != null) {
            runnable?.let(handler::removeCallbacks)

            val runnable = Runnable { onChange(newText) }
            this.runnable = runnable
            handler.postDelayed(runnable, delayMillis)

            true
        } else {
            false
        }

    companion object {
        private const val DEFAULT_DELAY = 500L
    }
}
