package hr.fer.drumre.rec.features.movies.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.drumre.rec.core.network.model.Movie
import hr.fer.drumre.rec.core.repository.MovieRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _data = MutableLiveData<Movie>()
    val data: LiveData<Movie>
        get() = _data

    private val _state = MutableLiveData<MovieDetailViewState>()
    val state: LiveData<MovieDetailViewState>
        get() = _state

    fun loadMovie(movieId: Long) {
        _state.postValue(MovieDetailViewState.Loading)
        viewModelScope.launch {
            try {
                val movie = movieRepository.getById(movieId)
                _data.postValue(movie)
                _state.postValue(MovieDetailViewState.Loaded)
            } catch (e: Exception) {
                _state.postValue(MovieDetailViewState.Error)
            }
        }
    }

    fun loadRandomMovie() {
        _state.postValue(MovieDetailViewState.Loading)
        viewModelScope.launch {
            try {
                val movie = movieRepository.getRandom()
                _data.postValue(movie)
                _state.postValue(MovieDetailViewState.Loaded)
            } catch (e: Exception) {
                _state.postValue(MovieDetailViewState.Error)
            }
        }
    }

    fun openNytReview(url: String?) {
        if (!url.isNullOrEmpty()) {
            _state.postValue(MovieDetailViewState.OpenNytReview(url))
        }
    }

    fun addMovieToFavorite() {
        _data.value?.let {
            viewModelScope.launch {
                try {
                    movieRepository.addToFavorites(it.id)
                    data.value?.copy(isFavorite = true)?.let(_data::postValue)
                    _state.postValue(MovieDetailViewState.AddedToFavorite)
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    fun removeMovieFromFavorite() {
        _data.value?.let {
            viewModelScope.launch {
                try {
                    movieRepository.removeFromFavorites(it.id)
                    data.value?.copy(isFavorite = false)?.let(_data::postValue)
                    _state.postValue(MovieDetailViewState.RemovedFromFavorite)
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    fun dismissCharacterDetail() {
        _state.postValue(MovieDetailViewState.Dismiss)
    }
}
