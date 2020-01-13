package hr.fer.drumre.rec.core.di.modules

import dagger.Binds
import dagger.Module
import hr.fer.drumre.rec.core.utils.ThemeUtils
import hr.fer.drumre.rec.core.utils.ThemeUtilsImpl
import hr.fer.drumre.rec.core.utils.UserPreferences
import hr.fer.drumre.rec.core.utils.UserPreferencesImpl
import javax.inject.Singleton

@Module
abstract class UtilsModule {

    @Singleton
    @Binds
    abstract fun bindThemeUtils(themeUtilsImpl: ThemeUtilsImpl): ThemeUtils

    @Singleton
    @Binds
    abstract fun bindUserPreferences(userPreferencesImpl: UserPreferencesImpl): UserPreferences
}
