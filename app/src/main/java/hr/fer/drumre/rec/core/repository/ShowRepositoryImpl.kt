package hr.fer.drumre.rec.core.repository

import hr.fer.drumre.rec.core.network.model.Show
import hr.fer.drumre.rec.core.network.services.ShowService
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val showService: ShowService
) : ShowRepository {
    override suspend fun getByQuery(query: String, offset: Int, limit: Int): List<Show> =
        if (query.isBlank()) {
            showService.getAll(offset, limit)
        } else {
            showService.search(query, offset, limit)
        }

    override suspend fun getRecommendations(offset: Int, limit: Int): List<Show> =
        showService.getRecommendations(offset, limit)

    override suspend fun getRandom(): Show =
        showService.getRandom()

    override suspend fun getFavorites(offset: Int, limit: Int): List<Show> =
        showService.getRecommendations(offset, limit)

    override suspend fun getById(showId: Long): Show =
        showService.getById(showId)

    override suspend fun addToFavorites(showId: Long) =
        showService.addToFavorites(showId)

    override suspend fun removeFromFavorites(showId: Long) =
        showService.removeFromFavorites(showId)
}
