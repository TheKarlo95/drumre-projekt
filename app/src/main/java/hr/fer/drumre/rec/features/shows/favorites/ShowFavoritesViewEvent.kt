package hr.fer.drumre.rec.features.shows.favorites

import hr.fer.drumre.rec.core.network.model.Show

sealed class ShowFavoritesViewEvent {
    data class OpenShowDetail(val show: Show) : ShowFavoritesViewEvent()
}
