package hr.fer.drumre.rec.features.movies.list

import hr.fer.drumre.rec.commons.ui.base.BaseViewState

sealed class MovieListViewState : BaseViewState {

    object Refreshing : MovieListViewState()

    object Loaded : MovieListViewState()

    object Loading : MovieListViewState()

    object AddLoading : MovieListViewState()

    object Empty : MovieListViewState()

    object Error : MovieListViewState()

    object AddError : MovieListViewState()

    object NoMoreElements : MovieListViewState()

    fun isRefreshing() = this is Refreshing

    fun isLoaded() = this is Loaded

    fun isLoading() = this is Loading

    fun isAddLoading() = this is AddLoading

    fun isEmpty() = this is Empty

    fun isError() = this is Error

    fun isAddError() = this is AddError

    fun isNoMoreElements() = this is NoMoreElements
}
