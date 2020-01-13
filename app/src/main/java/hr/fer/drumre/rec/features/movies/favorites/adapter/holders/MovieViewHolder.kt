package hr.fer.drumre.rec.features.movies.favorites.adapter.holders

import android.view.LayoutInflater
import hr.fer.drumre.rec.commons.ui.base.BaseViewHolder
import hr.fer.drumre.rec.core.network.model.Movie
import hr.fer.drumre.rec.databinding.FavoritesItemMovieBinding
import hr.fer.drumre.rec.features.movies.favorites.MovieFavoritesViewModel

class MovieViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<FavoritesItemMovieBinding>(binding = FavoritesItemMovieBinding.inflate(inflater)) {

    fun bind(viewModel: MovieFavoritesViewModel, item: Movie) {
        binding.viewModel = viewModel
        binding.movie = item
        binding.executePendingBindings()
    }
}
