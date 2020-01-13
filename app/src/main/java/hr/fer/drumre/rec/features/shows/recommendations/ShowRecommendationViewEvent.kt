package hr.fer.drumre.rec.features.shows.recommendations

import hr.fer.drumre.rec.core.network.model.Show

sealed class ShowRecommendationViewEvent {
    data class OpenShowDetail(val show: Show) : ShowRecommendationViewEvent()
}
