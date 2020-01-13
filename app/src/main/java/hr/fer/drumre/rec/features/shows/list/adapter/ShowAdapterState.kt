package hr.fer.drumre.rec.features.shows.list.adapter

sealed class ShowAdapterState(val hasExtraRow: Boolean) {

    object Added : ShowAdapterState(hasExtraRow = true)

    object AddLoading : ShowAdapterState(hasExtraRow = true)

    object AddError : ShowAdapterState(hasExtraRow = true)

    object NoMore : ShowAdapterState(hasExtraRow = false)

    fun isAdded() = this is Added

    fun isAddLoading() = this is AddLoading

    fun isAddError() = this is AddError

    fun isNoMore() = this is NoMore
}
