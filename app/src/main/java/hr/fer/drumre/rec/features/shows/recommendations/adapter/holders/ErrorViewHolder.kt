package hr.fer.drumre.rec.features.shows.recommendations.adapter.holders

import android.view.LayoutInflater
import hr.fer.drumre.rec.commons.ui.base.BaseViewHolder
import hr.fer.drumre.rec.databinding.RecommendationsItemShowErrorBinding
import hr.fer.drumre.rec.features.shows.recommendations.ShowRecommendationViewModel

class ErrorViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<RecommendationsItemShowErrorBinding>(
    binding = RecommendationsItemShowErrorBinding.inflate(inflater)
) {
    fun bind(viewModel: ShowRecommendationViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
