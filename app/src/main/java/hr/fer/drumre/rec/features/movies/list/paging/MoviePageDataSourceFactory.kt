package hr.fer.drumre.rec.features.movies.list.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import hr.fer.drumre.rec.core.network.model.Movie
import javax.inject.Inject
import javax.inject.Provider

class MoviePageDataSourceFactory @Inject constructor(
    private val providerDataSource: Provider<MoviePageDataSource>
) : DataSource.Factory<Int, Movie>() {

    var query: String = ""
        set(value) {
            field = value
            refresh()
        }

    var sourceLiveData = MutableLiveData<MoviePageDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val dataSource = providerDataSource.get()
        dataSource.query = query
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
