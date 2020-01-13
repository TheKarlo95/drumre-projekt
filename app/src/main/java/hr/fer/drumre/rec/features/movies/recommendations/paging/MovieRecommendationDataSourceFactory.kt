package hr.fer.drumre.rec.features.movies.recommendations.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import hr.fer.drumre.rec.features.movies.recommendations.paging.MovieRecommendationDataSource
import hr.fer.drumre.rec.core.network.model.Movie
import javax.inject.Inject
import javax.inject.Provider

class MovieRecommendationDataSourceFactory @Inject constructor(
    private val providerDataSource: Provider<MovieRecommendationDataSource>
) : DataSource.Factory<Int, Movie>() {

    var sourceLiveData = MutableLiveData<MovieRecommendationDataSource>()

    override fun create(): DataSource<Int, Movie> {
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
