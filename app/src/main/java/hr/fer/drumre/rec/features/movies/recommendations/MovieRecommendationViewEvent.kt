package hr.fer.drumre.rec.features.movies.recommendations

import hr.fer.drumre.rec.core.network.model.Movie

sealed class MovieRecommendationViewEvent {
    data class OpenMovieDetail(val movie: Movie) : MovieRecommendationViewEvent()
}
