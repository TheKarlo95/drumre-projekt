package hr.fer.drumre.rec.features.movies.recommendations

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import hr.fer.drumre.rec.commons.ui.livedata.SingleLiveData
import hr.fer.drumre.rec.core.network.NetworkState
import hr.fer.drumre.rec.core.network.model.Movie
import hr.fer.drumre.rec.features.movies.recommendations.paging.MovieRecommendationDataSource.Companion.PAGE_MAX_ELEMENTS
import hr.fer.drumre.rec.features.movies.recommendations.paging.MovieRecommendationDataSourceFactory
import javax.inject.Inject

class MovieRecommendationViewModel @Inject constructor(
    private val dataSourceFactory: MovieRecommendationDataSourceFactory
) : ViewModel() {

    private val networkState = Transformations.switchMap(dataSourceFactory.sourceLiveData) {
        it.networkState
    }

    val event = SingleLiveData<MovieRecommendationViewEvent>()
    val data = LivePagedListBuilder(dataSourceFactory, PAGE_MAX_ELEMENTS).build()
    val state = Transformations.map(networkState) {
        when (it) {
            is NetworkState.Success ->
                if (it.isAdditional && it.isEmptyResponse) {
                    MovieRecommendationViewState.NoMoreElements
                } else if (it.isEmptyResponse) {
                    MovieRecommendationViewState.Empty
                } else {
                    MovieRecommendationViewState.Loaded
                }
            is NetworkState.Loading ->
                if (it.isAdditional) {
                    MovieRecommendationViewState.AddLoading
                } else {
                    MovieRecommendationViewState.Loading
                }
            is NetworkState.Error ->
                if (it.isAdditional) {
                    MovieRecommendationViewState.AddError
                } else {
                    MovieRecommendationViewState.Error
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
        event.postValue(MovieRecommendationViewEvent.OpenMovieDetail(movie))
    }
}
