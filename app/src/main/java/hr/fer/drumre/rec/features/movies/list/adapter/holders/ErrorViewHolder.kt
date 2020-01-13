package hr.fer.drumre.rec.features.movies.list.adapter.holders

import android.view.LayoutInflater
import hr.fer.drumre.rec.commons.ui.base.BaseViewHolder
import hr.fer.drumre.rec.databinding.ListItemMovieErrorBinding
import hr.fer.drumre.rec.features.movies.list.MovieListViewModel

class ErrorViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemMovieErrorBinding>(
    binding = ListItemMovieErrorBinding.inflate(inflater)
) {
    fun bind(viewModel: MovieListViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
