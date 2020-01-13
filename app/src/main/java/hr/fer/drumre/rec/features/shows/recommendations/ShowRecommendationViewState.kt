package hr.fer.drumre.rec.features.shows.recommendations

import hr.fer.drumre.rec.commons.ui.base.BaseViewState

sealed class ShowRecommendationViewState : BaseViewState {

    object Refreshing : ShowRecommendationViewState()

    object Loaded : ShowRecommendationViewState()

    object Loading : ShowRecommendationViewState()

    object AddLoading : ShowRecommendationViewState()

    object Empty : ShowRecommendationViewState()

    object Error : ShowRecommendationViewState()

    object AddError : ShowRecommendationViewState()

    object NoMoreElements : ShowRecommendationViewState()

    fun isRefreshing() = this is Refreshing

    fun isLoaded() = this is Loaded

    fun isLoading() = this is Loading

    fun isAddLoading() = this is AddLoading

    fun isEmpty() = this is Empty

    fun isError() = this is Error

    fun isAddError() = this is AddError

    fun isNoMoreElements() = this is NoMoreElements
}
