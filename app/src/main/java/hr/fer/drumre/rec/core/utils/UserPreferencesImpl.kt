package hr.fer.drumre.rec.core.utils

import android.content.Context
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(context: Context) : UserPreferences {

    private val sharedPref = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

    override fun isLoggedIn(): Boolean = getAccessToken() != null

    override fun setFacebookAccessToken(accessToken: String) =
        set(FACEBOOK, accessToken)

    override fun setGoogleAccessToken(accessToken: String) =
        set(GOOGLE, accessToken)

    override fun set(provider: String?, accessToken: String?) {
        with(sharedPref.edit()) {
            putString(PROVIDER_KEY, provider)
            putString(ACCES_TOKEN_KEY, accessToken)
            commit()
        }
    }

    override fun getProvider(): String? = sharedPref.getString(PROVIDER_KEY, null)

    override fun getAccessToken(): String? = sharedPref.getString(ACCES_TOKEN_KEY, null)

    companion object {
        private const val PREFERENCE_FILE_KEY = "hr.fer.drumre.rec.USER"
        private const val PROVIDER_KEY = "provider"
        private const val ACCES_TOKEN_KEY = "access_token"

        private const val FACEBOOK = "Facebook"
        private const val GOOGLE = "Google"
    }
}
