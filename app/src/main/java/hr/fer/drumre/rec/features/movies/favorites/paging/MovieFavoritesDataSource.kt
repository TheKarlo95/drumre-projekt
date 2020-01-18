package hr.fer.drumre.rec.features.movies.favorites.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import hr.fer.drumre.rec.core.network.NetworkState
import hr.fer.drumre.rec.core.network.model.Movie
import hr.fer.drumre.rec.core.repository.MovieRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

open class MovieFavoritesDataSource @Inject constructor(
    private val movieRepository: MovieRepository,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Movie>() {

    val networkState = MutableLiveData<NetworkState>()
    var retry: (() -> Unit)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.Loading())
        scope.launch(CoroutineExceptionHandler { _, _ ->
            retry = {
                loadInitial(params, callback)
            }
            networkState.postValue(NetworkState.Error())
        }) {
            val response = movieRepository.getFavorites(
                offset = PAGE_INIT_ELEMENTS,
                limit = PAGE_MAX_ELEMENTS
            )
            callback.onResult(response, null, PAGE_MAX_ELEMENTS)
            networkState.postValue(NetworkState.Success(isEmptyResponse = response.isEmpty()))
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.Loading(true))
        scope.launch(CoroutineExceptionHandler { _, _ ->
            retry = { loadAfter(params, callback) }
            networkState.postValue(NetworkState.Error(true))
        }) {
            val response = movieRepository.getFavorites(
                offset = params.key,
                limit = PAGE_MAX_ELEMENTS
            )
            callback.onResult(response, params.key + PAGE_MAX_ELEMENTS)
            networkState.postValue(NetworkState.Success(true, response.isEmpty()))
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) = Unit

    fun retry() {
        retry?.invoke()
    }

    companion object {
        const val PAGE_INIT_ELEMENTS = 0
        const val PAGE_MAX_ELEMENTS = 50
    }
}
