package hr.fer.drumre.rec.features.movies.favorites.adapter.holders

import android.view.LayoutInflater
import hr.fer.drumre.rec.commons.ui.base.BaseViewHolder
import hr.fer.drumre.rec.databinding.FavoritesItemMovieErrorBinding
import hr.fer.drumre.rec.features.movies.favorites.MovieFavoritesViewModel

class ErrorViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<FavoritesItemMovieErrorBinding>(
    binding = FavoritesItemMovieErrorBinding.inflate(inflater)
) {
    fun bind(viewModel: MovieFavoritesViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
