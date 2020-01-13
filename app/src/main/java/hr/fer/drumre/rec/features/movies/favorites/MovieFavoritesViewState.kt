package hr.fer.drumre.rec.features.movies.favorites

import hr.fer.drumre.rec.commons.ui.base.BaseViewState

sealed class MovieFavoritesViewState : BaseViewState {

    object Refreshing : MovieFavoritesViewState()

    object Loaded : MovieFavoritesViewState()

    object Loading : MovieFavoritesViewState()

    object AddLoading : MovieFavoritesViewState()

    object Empty : MovieFavoritesViewState()

    object Error : MovieFavoritesViewState()

    object AddError : MovieFavoritesViewState()

    object NoMoreElements : MovieFavoritesViewState()

    fun isRefreshing() = this is Refreshing

    fun isLoaded() = this is Loaded

    fun isLoading() = this is Loading

    fun isAddLoading() = this is AddLoading

    fun isEmpty() = this is Empty

    fun isError() = this is Error

    fun isAddError() = this is AddError

    fun isNoMoreElements() = this is NoMoreElements
}
