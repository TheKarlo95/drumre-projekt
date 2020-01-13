package hr.fer.drumre.rec.commons.ui.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

@Suppress("UNCHECKED_CAST")
fun <VM : ViewModel> Fragment.viewModel(
    key: String? = null,
    factory: () -> VM
): VM {
    val factoryViewModel = factory()
    val viewModelProviderFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factoryViewModel as T
        }
    }

    return if (key != null) {
        ViewModelProviders.of(this, viewModelProviderFactory).get(key, factoryViewModel::class.java)
    } else {
        ViewModelProviders.of(this, viewModelProviderFactory).get(factoryViewModel::class.java)
    }
}

@Suppress("UNCHECKED_CAST")
fun <VM : ViewModel> AppCompatActivity.viewModel(
    key: String? = null,
    factory: () -> VM
): VM {
    val factoryViewModel = factory()
    val viewModelProviderFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factoryViewModel as T
        }
    }

    return if (key != null) {
        ViewModelProviders.of(this, viewModelProviderFactory).get(key, factoryViewModel::class.java)
    } else {
        ViewModelProviders.of(this, viewModelProviderFactory).get(factoryViewModel::class.java)
    }
}
