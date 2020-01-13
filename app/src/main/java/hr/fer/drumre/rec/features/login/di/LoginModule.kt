package hr.fer.drumre.rec.features.login.di

import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.commons.ui.extensions.viewModel
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.core.network.services.UserService
import hr.fer.drumre.rec.core.utils.UserPreferences
import hr.fer.drumre.rec.features.login.LoginActivity
import hr.fer.drumre.rec.features.login.LoginViewModel

@Module
class LoginModule(private val activity: LoginActivity) {

    @Provides
    @FeatureScope
    fun providesLoginViewModel(
        userPreferences: UserPreferences,
        userService: UserService
    ) = activity.viewModel { LoginViewModel(userPreferences, userService) }
}
