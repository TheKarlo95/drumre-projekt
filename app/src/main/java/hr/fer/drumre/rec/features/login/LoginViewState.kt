package hr.fer.drumre.rec.features.login

import hr.fer.drumre.rec.commons.ui.base.BaseViewState

sealed class LoginViewState : BaseViewState {

    object Success : LoginViewState()

    object Error : LoginViewState()

    fun isError() = this is Error
}
