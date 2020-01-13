package hr.fer.drumre.rec.core.network

import hr.fer.drumre.rec.core.utils.UserPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val userPreferences: UserPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val provider = "Facebook"
        val accessToken = "EAAp7V7iB4qoBAEiIaNqTJThXeOUX8xsLCXi4WHeFD300rhGUkvwDwrv3ZCgpqUxIfzqjyy7a8GFp0JZAWoLXlYSDWiMjxM7hf8y1R8KPp3VdVMDPhiel12qVx9mru8xZBI4oLTu0NESvrdTGMrCNBF2pM86fmXVyZAJ79HZBOCXKtdaBHS8ELrmFWIUZA9UXZCmNNvsZCv58SwZDZD"

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
