package hr.fer.drumre.rec.features.home.shows

import hr.fer.drumre.rec.commons.ui.base.BaseViewState

sealed class ShowsHomeViewState : BaseViewState {

    object FullScreen : ShowsHomeViewState()

    object NavigationScreen : ShowsHomeViewState()

    fun isFullscreen() = this is FullScreen

    fun isNavigationScreen() = this is NavigationScreen
}
