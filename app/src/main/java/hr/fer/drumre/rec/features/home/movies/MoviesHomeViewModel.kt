package hr.fer.drumre.rec.features.home.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import hr.fer.drumre.rec.R

open class MoviesHomeViewModel : ViewModel() {

    private val _state = MutableLiveData<MoviesHomeViewState>()
    val state: LiveData<MoviesHomeViewState>
        get() = _state

    fun navigationControllerChanged(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (MOVIE_NAV_FRAGMENTS_ID.contains(destination.id)) {
                _state.postValue(MoviesHomeViewState.NavigationScreen)
            } else {
                _state.postValue(MoviesHomeViewState.FullScreen)
            }
        }
    }

    companion object {
        @JvmStatic private val MOVIE_NAV_FRAGMENTS_ID = intArrayOf(
            R.id.movie_list_fragment,
            R.id.movie_recommendations_fragment,
            R.id.movie_favorites_fragment,
            R.id.random_movie_fragment
        )
    }
}
