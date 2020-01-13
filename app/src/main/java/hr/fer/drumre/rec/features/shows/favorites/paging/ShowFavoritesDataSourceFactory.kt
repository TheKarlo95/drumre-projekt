package hr.fer.drumre.rec.features.shows.favorites.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import hr.fer.drumre.rec.core.network.model.Show
import javax.inject.Inject
import javax.inject.Provider

class ShowFavoritesDataSourceFactory @Inject constructor(
    private val providerDataSource: Provider<ShowFavoritesDataSource>
) : DataSource.Factory<Int, Show>() {

    var sourceLiveData = MutableLiveData<ShowFavoritesDataSource>()

    override fun create(): DataSource<Int, Show> {
        val dataSource = providerDataSource.get()
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

    fun refresh() {
        sourceLiveData.value?.invalidate()
    }

    fun retry() {
        sourceLiveData.value?.retry()
    }
}
