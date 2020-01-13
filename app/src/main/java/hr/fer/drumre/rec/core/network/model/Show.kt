package hr.fer.drumre.rec.core.network.model

import android.os.Parcelable
import androidx.core.text.HtmlCompat
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Show(
    @SerializedName("id") val id: Long,
    @SerializedName("officialSite") val officialSite: String,
    @SerializedName("name") val name: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("rating") val rating: ShowRating,
    @SerializedName("image") val image: ShowImage?,
    @SerializedName("language") val originalLanguage: String,
    @SerializedName("genres") val genres: List<String>,
    @SerializedName("isFavourite") val isFavorite: Boolean
) : Parcelable {

    val formattedSummary: CharSequence
        get() = HtmlCompat.fromHtml(summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
}
