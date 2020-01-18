package hr.fer.drumre.rec.features.shows.list

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import hr.fer.drumre.rec.commons.ui.livedata.SingleLiveData
import hr.fer.drumre.rec.core.network.NetworkState
import hr.fer.drumre.rec.core.network.model.Show
import hr.fer.drumre.rec.features.shows.list.paging.ShowPageDataSource.Companion.PAGE_MAX_ELEMENTS
import hr.fer.drumre.rec.features.shows.list.paging.ShowPageDataSourceFactory
import javax.inject.Inject

class ShowListViewModel @Inject constructor(
    private val dataSourceFactory: ShowPageDataSourceFactory
) : ViewModel() {

    private val networkState = Transformations.switchMap(dataSourceFactory.sourceLiveData) {
        it.networkState
    }

    val event = SingleLiveData<ShowListViewEvent>()
    val data = LivePagedListBuilder(dataSourceFactory, PAGE_MAX_ELEMENTS).build()
    val state = Transformations.map(networkState) {
        when (it) {
            is NetworkState.Success ->
                if (it.isAdditional && it.isEmptyResponse) {
                    ShowListViewState.NoMoreElements
                } else if (it.isEmptyResponse) {
                    ShowListViewState.Empty
                } else {
                    ShowListViewState.Loaded
                }
            is NetworkState.Loading ->
                if (it.isAdditional) {
                    ShowListViewState.AddLoading
                } else {
                    ShowListViewState.Loading
                }
            is NetworkState.Error ->
                if (it.isAdditional) {
                    ShowListViewState.AddError
                } else {
                    ShowListViewState.Error
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

    fun openShowDetail(show: Show) {
        event.postValue(ShowListViewEvent.OpenShowDetail(show))
    }
}
