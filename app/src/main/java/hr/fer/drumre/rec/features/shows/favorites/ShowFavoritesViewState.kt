package hr.fer.drumre.rec.features.shows.favorites

import hr.fer.drumre.rec.commons.ui.base.BaseViewState

sealed class ShowFavoritesViewState : BaseViewState {

    object Refreshing : ShowFavoritesViewState()

    object Loaded : ShowFavoritesViewState()

    object Loading : ShowFavoritesViewState()

    object AddLoading : ShowFavoritesViewState()

    object Empty : ShowFavoritesViewState()

    object Error : ShowFavoritesViewState()

    object AddError : ShowFavoritesViewState()

    object NoMoreElements : ShowFavoritesViewState()

    fun isRefreshing() = this is Refreshing

    fun isLoaded() = this is Loaded

    fun isLoading() = this is Loading

    fun isAddLoading() = this is AddLoading

    fun isEmpty() = this is Empty

    fun isError() = this is Error

    fun isAddError() = this is AddError

    fun isNoMoreElements() = this is NoMoreElements
}
