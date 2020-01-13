package hr.fer.drumre.rec.features.shows.list

import hr.fer.drumre.rec.core.network.model.Show

sealed class ShowListViewEvent {
    data class OpenShowDetail(val show: Show) : ShowListViewEvent()
}
