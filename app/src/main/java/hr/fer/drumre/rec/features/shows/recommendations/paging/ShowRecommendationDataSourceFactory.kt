package hr.fer.drumre.rec.features.shows.recommendations.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import hr.fer.drumre.rec.core.network.model.Show
import javax.inject.Inject
import javax.inject.Provider

class ShowRecommendationDataSourceFactory @Inject constructor(
    private val providerDataSource: Provider<ShowRecommendationDataSource>
) : DataSource.Factory<Int, Show>() {

    var sourceLiveData = MutableLiveData<ShowRecommendationDataSource>()

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
