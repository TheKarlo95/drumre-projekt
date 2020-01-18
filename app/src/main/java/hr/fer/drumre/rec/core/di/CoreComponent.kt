package hr.fer.drumre.rec.core.di

import android.content.Context
import dagger.Component
import hr.fer.drumre.rec.core.di.modules.ContextModule
import hr.fer.drumre.rec.core.di.modules.NetworkModule
import hr.fer.drumre.rec.core.di.modules.UtilsModule
import hr.fer.drumre.rec.core.network.services.UserService
import hr.fer.drumre.rec.core.repository.MovieRepository
import hr.fer.drumre.rec.core.repository.ShowRepository
import hr.fer.drumre.rec.core.utils.ThemeUtils
import hr.fer.drumre.rec.core.utils.UserPreferences
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        UtilsModule::class
    ]
)
interface CoreComponent {

    fun context(): Context

    fun movieRepository(): MovieRepository

    fun showRepository(): ShowRepository

    fun userService(): UserService

    fun userPreferences(): UserPreferences

    fun themeUtils(): ThemeUtils
}
