package hr.fer.drumre.rec.features.movies.favorites

import hr.fer.drumre.rec.core.network.model.Movie

sealed class MovieFavoritesViewEvent {
    data class OpenMovieDetail(val movie: Movie) : MovieFavoritesViewEvent()
}
