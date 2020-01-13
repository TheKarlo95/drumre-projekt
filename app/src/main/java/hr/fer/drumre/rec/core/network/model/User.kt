package hr.fer.drumre.rec.core.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val username: String,
    @SerializedName("identifier") val identifier: String,
    @SerializedName("picture") val picture: String,
    @SerializedName("provider") val provider: String,
    @SerializedName("accessToken") val accessToken: String
) : Parcelable
