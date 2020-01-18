package hr.fer.drumre.rec.core.repository

import hr.fer.drumre.rec.core.network.model.Movie
import hr.fer.drumre.rec.core.network.services.MovieService
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRepository {
    override suspend fun getByQuery(query: String, offset: Int, limit: Int): List<Movie> =
        if (query.isBlank()) {
            movieService.getAll(offset, limit)
        } else {
            movieService.search(query, offset, limit)
        }

    override suspend fun getRecommendations(offset: Int, limit: Int): List<Movie> =
        movieService.getRecommendations(offset, limit)

    override suspend fun getRandom(): Movie =
        movieService.getRandom()

    override suspend fun getFavorites(offset: Int, limit: Int): List<Movie> =
        movieService.getRecommendations(offset, limit)

    override suspend fun getById(movieId: Long): Movie =
        movieService.getById(movieId)

    override suspend fun addToFavorites(movieId: Long) =
        movieService.addToFavorites(movieId)

    override suspend fun removeFromFavorites(movieId: Long) =
        movieService.removeFromFavorites(movieId)
}
