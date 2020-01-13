package hr.fer.drumre.rec.core.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NyReview(
    val link: String,
    val headline: String
) : Parcelable
