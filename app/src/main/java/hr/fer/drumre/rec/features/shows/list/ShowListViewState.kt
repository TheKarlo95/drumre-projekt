package hr.fer.drumre.rec.features.shows.list

import hr.fer.drumre.rec.commons.ui.base.BaseViewState

sealed class ShowListViewState : BaseViewState {

    object Refreshing : ShowListViewState()

    object Loaded : ShowListViewState()

    object Loading : ShowListViewState()

    object AddLoading : ShowListViewState()

    object Empty : ShowListViewState()

    object Error : ShowListViewState()

    object AddError : ShowListViewState()

    object NoMoreElements : ShowListViewState()

    fun isRefreshing() = this is Refreshing

    fun isLoaded() = this is Loaded

    fun isLoading() = this is Loading

    fun isAddLoading() = this is AddLoading

    fun isEmpty() = this is Empty

    fun isError() = this is Error

    fun isAddError() = this is AddError

    fun isNoMoreElements() = this is NoMoreElements
}
