package hr.fer.drumre.rec.features.movies.list

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import hr.fer.drumre.rec.commons.ui.livedata.SingleLiveData
import hr.fer.drumre.rec.core.network.NetworkState
import hr.fer.drumre.rec.core.network.model.Movie
import hr.fer.drumre.rec.features.movies.list.paging.MoviePageDataSource.Companion.PAGE_MAX_ELEMENTS
import hr.fer.drumre.rec.features.movies.list.paging.MoviePageDataSourceFactory
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val dataSourceFactory: MoviePageDataSourceFactory
) : ViewModel() {

    private val networkState = Transformations.switchMap(dataSourceFactory.sourceLiveData) {
        it.networkState
    }

    val event = SingleLiveData<MovieListViewEvent>()
    val data = LivePagedListBuilder(dataSourceFactory, PAGE_MAX_ELEMENTS).build()
    val state = Transformations.map(networkState) {
        when (it) {
            is NetworkState.Success ->
                if (it.isAdditional && it.isEmptyResponse) {
                    MovieListViewState.NoMoreElements
                } else if (it.isEmptyResponse) {
                    MovieListViewState.Empty
                } else {
                    MovieListViewState.Loaded
                }
            is NetworkState.Loading ->
                if (it.isAdditional) {
                    MovieListViewState.AddLoading
                } else {
                    MovieListViewState.Loading
                }
            is NetworkState.Error ->
                if (it.isAdditional) {
                    MovieListViewState.AddError
                } else {
                    MovieListViewState.Error
                }
        }
    }

    fun changeQuery(query: String) {
        if (query != dataSourceFactory.query) {
            dataSourceFactory.query = query
        }
    }

    fun refreshLoadedCharactersList() {
        dataSourceFactory.refresh()
    }

    fun retryAddCharactersList() {
        dataSourceFactory.retry()
    }

    fun openMovieDetail(movie: Movie) {
        event.postValue(MovieListViewEvent.OpenMovieDetail(movie))
    }
}
