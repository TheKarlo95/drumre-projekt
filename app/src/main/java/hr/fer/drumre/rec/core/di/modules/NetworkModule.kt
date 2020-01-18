package hr.fer.drumre.rec.core.di.modules

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.core.network.AuthorizationInterceptor
import hr.fer.drumre.rec.core.network.services.MovieService
import hr.fer.drumre.rec.core.network.services.ShowService
import hr.fer.drumre.rec.core.network.services.UserService
import hr.fer.drumre.rec.core.repository.MovieRepository
import hr.fer.drumre.rec.core.repository.MovieRepositoryImpl
import hr.fer.drumre.rec.core.repository.ShowRepository
import hr.fer.drumre.rec.core.repository.ShowRepositoryImpl
import hr.fer.drumre.rec.core.utils.UserPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideAuthorizationInterceptor(
        userPreferences: UserPreferences
    ): AuthorizationInterceptor = AuthorizationInterceptor(userPreferences)

    @Singleton
    @Provides
    fun provideHttpClient(
        authorizationInterceptor: AuthorizationInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(authorizationInterceptor)
        clientBuilder.addInterceptor(loggingInterceptor)
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(client: OkHttpClient) =
        Retrofit.Builder()
            .client(client)
            .baseUrl("https://dm2019.azurewebsites.net/api/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        .create()
                )
            )
            .build()

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideShowService(retrofit: Retrofit) = retrofit.create(ShowService::class.java)

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit) = retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideMovieRepository(movieService: MovieService): MovieRepository =
        MovieRepositoryImpl(movieService)

    @Singleton
    @Provides
    fun provideShowRepository(showService: ShowService): ShowRepository =
        ShowRepositoryImpl(showService)
}
