package hr.fer.drumre.rec.core.network.services

import hr.fer.drumre.rec.core.network.model.Movie
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("Movies")
    suspend fun getAll(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Movie>

    @GET("Recommendation/Movies")
    suspend fun getRecommendations(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Movie>

    @GET("Recommendation/Movies/Random")
    suspend fun getRandom(): Movie

    @GET("Favourites/Movie")
    suspend fun getFavorites(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Movie>

    @GET("Movies/Search")
    suspend fun search(
        @Query("query") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Movie>

    @PUT("Movies/{id}")
    suspend fun getById(
        @Path("id") movieId: Long
    ): Movie

    @PUT("Favourites/Movie")
    suspend fun addToFavorites(
        @Query("movieId") movieId: Long
    )

    @DELETE("Favourites/Movie")
    suspend fun removeFromFavorites(
        @Query("movieId") movieId: Long
    )
}
