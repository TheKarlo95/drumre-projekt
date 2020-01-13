package hr.fer.drumre.rec.features.home.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import hr.fer.drumre.rec.R

open class ShowsHomeViewModel : ViewModel() {

    private val _state = MutableLiveData<ShowsHomeViewState>()
    val state: LiveData<ShowsHomeViewState>
        get() = _state

    fun navigationControllerChanged(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (SHOW_NAV_FRAGMENTS_ID.contains(destination.id)) {
                _state.postValue(ShowsHomeViewState.NavigationScreen)
            } else {
                _state.postValue(ShowsHomeViewState.FullScreen)
            }
        }
    }

    companion object {
        @JvmStatic private val SHOW_NAV_FRAGMENTS_ID = intArrayOf(
            R.id.show_list_fragment,
            R.id.show_recommendations_fragment,
            R.id.show_favorites_fragment
        )
    }
}
