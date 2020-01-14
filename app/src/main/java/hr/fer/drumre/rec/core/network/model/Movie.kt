package hr.fer.drumre.rec.core.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.time.Instant
import java.time.LocalDate
import java.util.*

@Parcelize
data class Movie(
    @SerializedName("id") val id: Long,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("release_date") val releaseDate: Date?,
    @SerializedName("nyt_review") val nyReview: NyReview? = null,
    @SerializedName("isFavourite") val isFavorite: Boolean
) : Parcelable {
    val image: String
        get() = if (posterPath?.startsWith("http") == true) {
            posterPath
        } else {
            "https://image.tmdb.org/t/p/w500$posterPath"
        }
}
