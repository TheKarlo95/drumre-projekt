package hr.fer.drumre.rec.core.repository

import hr.fer.drumre.rec.core.network.model.Movie

interface MovieRepository {
    suspend fun getByQuery(query: String = "", offset: Int, limit: Int): List<Movie>

    suspend fun getRecommendations(offset: Int, limit: Int): List<Movie>

    suspend fun getRandom(): Movie

    suspend fun getFavorites(offset: Int, limit: Int): List<Movie>

    suspend fun getById(movieId: Long): Movie

    suspend fun addToFavorites(movieId: Long)

    suspend fun removeFromFavorites(movieId: Long)
}
