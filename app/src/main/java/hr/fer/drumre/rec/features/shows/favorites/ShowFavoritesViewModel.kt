package hr.fer.drumre.rec.features.shows.favorites

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import hr.fer.drumre.rec.commons.ui.livedata.SingleLiveData
import hr.fer.drumre.rec.core.network.NetworkState
import hr.fer.drumre.rec.core.network.model.Show
import hr.fer.drumre.rec.features.shows.favorites.paging.ShowFavoritesDataSource.Companion.PAGE_MAX_ELEMENTS
import hr.fer.drumre.rec.features.shows.favorites.paging.ShowFavoritesDataSourceFactory
import javax.inject.Inject

class ShowFavoritesViewModel @Inject constructor(
    private val dataSourceFactory: ShowFavoritesDataSourceFactory
) : ViewModel() {

    private val networkState = Transformations.switchMap(dataSourceFactory.sourceLiveData) {
        it.networkState
    }

    val event = SingleLiveData<ShowFavoritesViewEvent>()
    val data = LivePagedListBuilder(dataSourceFactory, PAGE_MAX_ELEMENTS).build()
    val state = Transformations.map(networkState) {
        when (it) {
            is NetworkState.Success ->
                if (it.isAdditional && it.isEmptyResponse) {
                    ShowFavoritesViewState.NoMoreElements
                } else if (it.isEmptyResponse) {
                    ShowFavoritesViewState.Empty
                } else {
                    ShowFavoritesViewState.Loaded
                }
            is NetworkState.Loading ->
                if (it.isAdditional) {
                    ShowFavoritesViewState.AddLoading
                } else {
                    ShowFavoritesViewState.Loading
                }
            is NetworkState.Error ->
                if (it.isAdditional) {
                    ShowFavoritesViewState.AddError
                } else {
                    ShowFavoritesViewState.Error
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
        event.postValue(ShowFavoritesViewEvent.OpenShowDetail(show))
    }
}
