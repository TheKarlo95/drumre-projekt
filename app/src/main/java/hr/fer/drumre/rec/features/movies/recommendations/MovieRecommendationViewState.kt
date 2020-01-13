package hr.fer.drumre.rec.features.movies.recommendations

import hr.fer.drumre.rec.commons.ui.base.BaseViewState

sealed class MovieRecommendationViewState : BaseViewState {

    object Refreshing : MovieRecommendationViewState()

    object Loaded : MovieRecommendationViewState()

    object Loading : MovieRecommendationViewState()

    object AddLoading : MovieRecommendationViewState()

    object Empty : MovieRecommendationViewState()

    object Error : MovieRecommendationViewState()

    object AddError : MovieRecommendationViewState()

    object NoMoreElements : MovieRecommendationViewState()

    fun isRefreshing() = this is Refreshing

    fun isLoaded() = this is Loaded

    fun isLoading() = this is Loading

    fun isAddLoading() = this is AddLoading

    fun isEmpty() = this is Empty

    fun isError() = this is Error

    fun isAddError() = this is AddError

    fun isNoMoreElements() = this is NoMoreElements
}
