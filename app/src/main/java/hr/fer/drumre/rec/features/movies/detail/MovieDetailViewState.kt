package hr.fer.drumre.rec.features.movies.detail

import hr.fer.drumre.rec.commons.ui.base.BaseViewState

sealed class MovieDetailViewState : BaseViewState {

    object Loading : MovieDetailViewState()

    object Error : MovieDetailViewState()

    data class OpenNytReview(val url: String) : MovieDetailViewState()

    object AddedToFavorite : MovieDetailViewState()

    object RemovedFromFavorite : MovieDetailViewState()

    object Loaded : MovieDetailViewState()

    object Dismiss : MovieDetailViewState()

    fun isLoading() = this is Loading

    fun isError() = this is Error

    fun isOpenNytReview() = this is OpenNytReview

    fun isAddedToFavorite() = this is AddedToFavorite

    fun isRemovedFromFavorite() = this is RemovedFromFavorite

    fun isLoaded() = this is Loaded

    fun isDismiss() = this is Dismiss
}
