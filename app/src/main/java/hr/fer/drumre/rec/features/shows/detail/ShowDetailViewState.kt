package hr.fer.drumre.rec.features.shows.detail

import hr.fer.drumre.rec.commons.ui.base.BaseViewState

sealed class ShowDetailViewState : BaseViewState {

    object Loading : ShowDetailViewState()

    object Error : ShowDetailViewState()

    object AddedToFavorite : ShowDetailViewState()

    object RemovedFromFavorite : ShowDetailViewState()

    object Loaded : ShowDetailViewState()

    object Dismiss : ShowDetailViewState()

    fun isLoading() = this is Loading

    fun isError() = this is Error

    fun isAddedToFavorite() = this is AddedToFavorite

    fun isRemovedFromFavorite() = this is RemovedFromFavorite

    fun isLoaded() = this is Loaded

    fun isDismiss() = this is Dismiss
}
