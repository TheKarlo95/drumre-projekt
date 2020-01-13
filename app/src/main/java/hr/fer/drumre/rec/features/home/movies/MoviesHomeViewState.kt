package hr.fer.drumre.rec.features.home.movies

import hr.fer.drumre.rec.commons.ui.base.BaseViewState

sealed class MoviesHomeViewState : BaseViewState {

    object FullScreen : MoviesHomeViewState()

    object NavigationScreen : MoviesHomeViewState()

    fun isFullscreen() = this is FullScreen

    fun isNavigationScreen() = this is NavigationScreen
}
