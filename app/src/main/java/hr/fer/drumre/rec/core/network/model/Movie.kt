package hr.fer.drumre.rec.core.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import hr.fer.drumre.rec.core.network.model.Genre
import hr.fer.drumre.rec.core.network.model.NyReview
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id") val id: Long,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("genres") val genres: List<Genre>,
   // @SerializedName("release_date") val releaseDate: LocalDate?,
    @SerializedName("nyt_review") val nyReview: NyReview? = null,
    @SerializedName("isFavourite") val isFavorite: Boolean
) : Parcelable {
    val image: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"
}
