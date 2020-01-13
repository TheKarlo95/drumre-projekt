package hr.fer.drumre.rec.features.movies.recommendations.adapter.holders

import android.view.LayoutInflater
import hr.fer.drumre.rec.commons.ui.base.BaseViewHolder
import hr.fer.drumre.rec.databinding.RecommendationsItemMovieErrorBinding
import hr.fer.drumre.rec.features.movies.recommendations.MovieRecommendationViewModel

class ErrorViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<RecommendationsItemMovieErrorBinding>(
    binding = RecommendationsItemMovieErrorBinding.inflate(
        inflater
    )
) {
    fun bind(viewModel: MovieRecommendationViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
