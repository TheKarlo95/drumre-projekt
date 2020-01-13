package hr.fer.drumre.rec.features.movies.recommendations.adapter.holders

import android.view.LayoutInflater
import hr.fer.drumre.rec.commons.ui.base.BaseViewHolder
import hr.fer.drumre.rec.core.network.model.Movie
import hr.fer.drumre.rec.databinding.RecommendationsItemMovieBinding
import hr.fer.drumre.rec.features.movies.recommendations.MovieRecommendationViewModel

class MovieViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<RecommendationsItemMovieBinding>(binding = RecommendationsItemMovieBinding.inflate(inflater)) {

    fun bind(viewModel: MovieRecommendationViewModel, item: Movie) {
        binding.viewModel = viewModel
        binding.movie = item
        binding.executePendingBindings()
    }
}
