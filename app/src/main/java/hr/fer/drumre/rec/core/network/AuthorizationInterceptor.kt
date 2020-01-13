package hr.fer.drumre.rec.core.network

import hr.fer.drumre.rec.core.utils.UserPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val userPreferences: UserPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val provider = userPreferences.getProvider()
        val accessToken = userPreferences.getAccessToken()

        val newUrl = chain.request().url.newBuilder()
            .addQueryParameter("provider", provider)
            .addQueryParameter("accessToken", accessToken)
            .build()

        val newRequest = chain.request().newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
