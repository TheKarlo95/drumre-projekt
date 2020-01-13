package hr.fer.drumre.rec.features.shows.list.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import hr.fer.drumre.rec.core.network.model.Show
import javax.inject.Inject
import javax.inject.Provider

class ShowPageDataSourceFactory @Inject constructor(
    private val providerDataSource: Provider<ShowPageDataSource>
) : DataSource.Factory<Int, Show>() {

    var sourceLiveData = MutableLiveData<ShowPageDataSource>()

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
