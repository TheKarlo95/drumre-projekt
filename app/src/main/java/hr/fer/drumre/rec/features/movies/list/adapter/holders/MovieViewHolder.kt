package hr.fer.drumre.rec.features.movies.list.adapter.holders

import android.view.LayoutInflater
import hr.fer.drumre.rec.commons.ui.base.BaseViewHolder
import hr.fer.drumre.rec.core.network.model.Movie
import hr.fer.drumre.rec.databinding.ListItemMovieBinding
import hr.fer.drumre.rec.features.movies.list.MovieListViewModel

class MovieViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemMovieBinding>(binding = ListItemMovieBinding.inflate(inflater)) {

    fun bind(viewModel: MovieListViewModel, item: Movie) {
        binding.viewModel = viewModel
        binding.movie = item
        binding.executePendingBindings()
    }
}
