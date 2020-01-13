package hr.fer.drumre.rec.features.movies.recommendations.adapter

sealed class MovieAdapterState(val hasExtraRow: Boolean) {

    object Added : MovieAdapterState(hasExtraRow = true)

    object AddLoading : MovieAdapterState(hasExtraRow = true)

    object AddError : MovieAdapterState(hasExtraRow = true)

    object NoMore : MovieAdapterState(hasExtraRow = false)

    fun isAdded() = this is Added

    fun isAddLoading() = this is AddLoading

    fun isAddError() = this is AddError

    fun isNoMore() = this is NoMore
}
