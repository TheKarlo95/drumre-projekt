package hr.fer.drumre.rec.features.movies.list

import hr.fer.drumre.rec.core.network.model.Movie

sealed class MovieListViewEvent {
    data class OpenMovieDetail(val movie: Movie) : MovieListViewEvent()
}
