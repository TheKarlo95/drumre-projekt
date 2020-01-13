package hr.fer.drumre.rec.core.utils

interface UserPreferences {

    fun isLoggedIn(): Boolean

    fun setFacebookAccessToken(accessToken: String)

    fun setGoogleAccessToken(accessToken: String)

    fun set(provider: String?, accessToken: String?)

    fun getProvider(): String?

    fun getAccessToken(): String?
}
