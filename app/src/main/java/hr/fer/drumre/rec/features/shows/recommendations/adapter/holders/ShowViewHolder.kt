package hr.fer.drumre.rec.features.shows.recommendations.adapter.holders

import android.view.LayoutInflater
import hr.fer.drumre.rec.commons.ui.base.BaseViewHolder
import hr.fer.drumre.rec.core.network.model.Show
import hr.fer.drumre.rec.databinding.RecommendationsItemShowBinding
import hr.fer.drumre.rec.features.shows.recommendations.ShowRecommendationViewModel

class ShowViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<RecommendationsItemShowBinding>(binding = RecommendationsItemShowBinding.inflate(inflater)) {

    fun bind(viewModel: ShowRecommendationViewModel, item: Show) {
        binding.viewModel = viewModel
        binding.show = item
        binding.executePendingBindings()
    }
}
