package hr.fer.drumre.rec.features.shows.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.drumre.rec.core.network.model.Show
import hr.fer.drumre.rec.core.repository.ShowRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ShowDetailViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel() {

    private val _data = MutableLiveData<Show>()
    val data: LiveData<Show>
        get() = _data

    private val _state = MutableLiveData<ShowDetailViewState>()
    val state: LiveData<ShowDetailViewState>
        get() = _state

    fun loadShow(showId: Long) {
        _state.postValue(ShowDetailViewState.Loading)
        viewModelScope.launch {
            try {
                val show = showRepository.getById(showId)
                _data.postValue(show)
                _state.postValue(ShowDetailViewState.Loaded)
            } catch (e: Exception) {
                _state.postValue(ShowDetailViewState.Error)
            }
        }
    }

    fun loadRandomShow() {
        _state.postValue(ShowDetailViewState.Loading)
        viewModelScope.launch {
            try {
                val show = showRepository.getRandom()
                _data.postValue(show)
                _state.postValue(ShowDetailViewState.Loaded)
            } catch (e: Exception) {
                _state.postValue(ShowDetailViewState.Error)
            }
        }
    }

    fun addShowToFavorite() {
        _data.value?.let {
            viewModelScope.launch {
                try {
                    showRepository.addToFavorites(it.id)
                    _data.postValue(it.copy(isFavorite = true))
                    _state.postValue(ShowDetailViewState.AddedToFavorite)
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    fun removeShowFromFavorite() {
        _data.value?.let {
            viewModelScope.launch {
                try {
                    showRepository.removeFromFavorites(it.id)
                    _data.postValue(it.copy(isFavorite = false))
                    _state.postValue(ShowDetailViewState.RemovedFromFavorite)
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    fun dismissCharacterDetail() {
        _state.postValue(ShowDetailViewState.Dismiss)
    }
}
