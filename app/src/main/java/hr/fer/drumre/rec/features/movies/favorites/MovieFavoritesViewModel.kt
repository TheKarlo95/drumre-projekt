package hr.fer.drumre.rec.features.movies.favorites

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import hr.fer.drumre.rec.features.movies.favorites.MovieFavoritesViewEvent
import hr.fer.drumre.rec.features.movies.favorites.MovieFavoritesViewState
import hr.fer.drumre.rec.commons.ui.livedata.SingleLiveData
import hr.fer.drumre.rec.core.network.NetworkState
import hr.fer.drumre.rec.core.network.model.Movie
import hr.fer.drumre.rec.features.movies.favorites.paging.MovieFavoritesDataSource.Companion.PAGE_MAX_ELEMENTS
import hr.fer.drumre.rec.features.movies.favorites.paging.MovieFavoritesDataSourceFactory
import javax.inject.Inject

class MovieFavoritesViewModel @Inject constructor(
    private val dataSourceFactory: MovieFavoritesDataSourceFactory
) : ViewModel() {

    private val networkState = Transformations.switchMap(dataSourceFactory.sourceLiveData) {
        it.networkState
    }

    val event = SingleLiveData<MovieFavoritesViewEvent>()
    val data = LivePagedListBuilder(dataSourceFactory, PAGE_MAX_ELEMENTS).build()
    val state = Transformations.map(networkState) {
        when (it) {
            is NetworkState.Success ->
                if (it.isAdditional && it.isEmptyResponse) {
                    MovieFavoritesViewState.NoMoreElements
                } else if (it.isEmptyResponse) {
                    MovieFavoritesViewState.Empty
                } else {
                    MovieFavoritesViewState.Loaded
                }
            is NetworkState.Loading ->
                if (it.isAdditional) {
                    MovieFavoritesViewState.AddLoading
                } else {
                    MovieFavoritesViewState.Loading
                }
            is NetworkState.Error ->
                if (it.isAdditional) {
                    MovieFavoritesViewState.AddError
                } else {
                    MovieFavoritesViewState.Error
                }
        }
    }

    fun refreshLoadedCharactersList() {
        dataSourceFactory.refresh()
    }

    fun retryAddCharactersList() {
        dataSourceFactory.retry()
    }

    fun openMovieDetail(movie: Movie) {
        event.postValue(MovieFavoritesViewEvent.OpenMovieDetail(movie))
    }
}
