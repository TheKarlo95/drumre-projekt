package hr.fer.drumre.rec.features.shows.recommendations

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import hr.fer.drumre.rec.commons.ui.livedata.SingleLiveData
import hr.fer.drumre.rec.core.network.NetworkState
import hr.fer.drumre.rec.core.network.model.Show
import hr.fer.drumre.rec.features.shows.recommendations.paging.ShowRecommendationDataSource.Companion.PAGE_MAX_ELEMENTS
import hr.fer.drumre.rec.features.shows.recommendations.paging.ShowRecommendationDataSourceFactory
import javax.inject.Inject

class ShowRecommendationViewModel @Inject constructor(
    private val dataSourceFactory: ShowRecommendationDataSourceFactory
) : ViewModel() {

    private val networkState = Transformations.switchMap(dataSourceFactory.sourceLiveData) {
        it.networkState
    }

    val event = SingleLiveData<ShowRecommendationViewEvent>()
    val data = LivePagedListBuilder(dataSourceFactory, PAGE_MAX_ELEMENTS).build()
    val state = Transformations.map(networkState) {
        when (it) {
            is NetworkState.Success ->
                if (it.isAdditional && it.isEmptyResponse) {
                    ShowRecommendationViewState.NoMoreElements
                } else if (it.isEmptyResponse) {
                    ShowRecommendationViewState.Empty
                } else {
                    ShowRecommendationViewState.Loaded
                }
            is NetworkState.Loading ->
                if (it.isAdditional) {
                    ShowRecommendationViewState.AddLoading
                } else {
                    ShowRecommendationViewState.Loading
                }
            is NetworkState.Error ->
                if (it.isAdditional) {
                    ShowRecommendationViewState.AddError
                } else {
                    ShowRecommendationViewState.Error
                }
        }
    }

    fun refreshLoadedCharactersList() {
        dataSourceFactory.refresh()
    }

    fun retryAddCharactersList() {
        dataSourceFactory.retry()
    }

    fun openShowDetail(show: Show) {
        event.postValue(ShowRecommendationViewEvent.OpenShowDetail(show))
    }
}
