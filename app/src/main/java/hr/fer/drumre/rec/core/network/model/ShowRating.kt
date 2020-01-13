package hr.fer.drumre.rec.core.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import hr.fer.drumre.rec.core.network.model.Genre
import hr.fer.drumre.rec.core.network.model.NyReview
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShowRating(
    @SerializedName("average") val average: Double?
) : Parcelable
