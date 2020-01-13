package hr.fer.drumre.rec.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.drumre.rec.core.network.services.UserService
import hr.fer.drumre.rec.core.utils.UserPreferences
import kotlinx.coroutines.launch
import javax.inject.Inject

open class LoginViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val userService: UserService
) : ViewModel() {
    fun loginWithFacebook(accessToken: String?) {
        if (accessToken != null) {
            userPreferences.setFacebookAccessToken(accessToken)
            viewModelScope.launch {
                userService.login()
            }
        }
    }

    fun loginWithGoogle(accessToken: String?) {
        if (accessToken != null) {
            userPreferences.setGoogleAccessToken(accessToken)
            viewModelScope.launch {
                userService.login()
            }
        }
    }
}
