package hr.fer.drumre.rec.features.shows.recommendations.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import hr.fer.drumre.rec.core.network.NetworkState
import hr.fer.drumre.rec.core.network.model.Show
import hr.fer.drumre.rec.core.network.services.ShowService
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

open class ShowRecommendationDataSource @Inject constructor(
    private val showService: ShowService,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Show>() {

    val networkState = MutableLiveData<NetworkState>()
    var retry: (() -> Unit)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Show>
    ) {
        networkState.postValue(NetworkState.Loading())
        scope.launch(CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            retry = {
                loadInitial(params, callback)
            }
            networkState.postValue(NetworkState.Error())
        }) {
            val response = showService.getRecommendations(
                offset = PAGE_INIT_ELEMENTS,
                limit = PAGE_MAX_ELEMENTS
            )
            callback.onResult(response, null, PAGE_MAX_ELEMENTS)
            networkState.postValue(NetworkState.Success(isEmptyResponse = response.isEmpty()))
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Show>
    ) {
        networkState.postValue(NetworkState.Loading(true))
        scope.launch(CoroutineExceptionHandler { _, _ ->
            retry = {
                loadAfter(params, callback)
            }
            networkState.postValue(NetworkState.Error(true))
        }) {
            val response = showService.getRecommendations(
                offset = params.key,
                limit = PAGE_MAX_ELEMENTS
            )
            callback.onResult(response, params.key + PAGE_MAX_ELEMENTS)
            networkState.postValue(NetworkState.Success(true, response.isEmpty()))
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Show>
    ) = Unit

    fun retry() {
        retry?.invoke()
    }

    companion object {
        const val PAGE_INIT_ELEMENTS = 0
        const val PAGE_MAX_ELEMENTS = 50
    }
}
