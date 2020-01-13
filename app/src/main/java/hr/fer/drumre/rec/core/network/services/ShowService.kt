package hr.fer.drumre.rec.core.network.services

import hr.fer.drumre.rec.core.network.model.Show
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowService {

    @GET("Shows")
    suspend fun getAll(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Show>

    @GET("Recommendation/Shows")
    suspend fun getRecommendations(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Show>

    @GET("Favourites/Show")
    suspend fun getFavorites(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Show>

    @GET("Recommendation/Shows/Random")
    suspend fun getRandom(): Show

    @PUT("Shows/{id}")
    suspend fun getById(
        @Path("id") movieId: Long
    ): Show

    @PUT("Favourites/Show")
    suspend fun addToFavorites(
        @Query("movieId") movieId: Long
    )

    @DELETE("Favourites/Show")
    suspend fun removeFromFavorites(
        @Query("movieId") movieId: Long
    )
}
