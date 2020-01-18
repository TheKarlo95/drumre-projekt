package hr.fer.drumre.rec.core.repository

import hr.fer.drumre.rec.core.network.model.Show

interface ShowRepository {
    suspend fun getByQuery(query: String = "", offset: Int, limit: Int): List<Show>

    suspend fun getRecommendations(offset: Int, limit: Int): List<Show>

    suspend fun getRandom(): Show

    suspend fun getFavorites(offset: Int, limit: Int): List<Show>

    suspend fun getById(showId: Long): Show

    suspend fun addToFavorites(showId: Long)

    suspend fun removeFromFavorites(showId: Long)
}
