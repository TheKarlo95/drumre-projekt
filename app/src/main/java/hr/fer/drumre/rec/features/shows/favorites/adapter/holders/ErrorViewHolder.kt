package hr.fer.drumre.rec.features.shows.favorites.adapter.holders

import android.view.LayoutInflater
import hr.fer.drumre.rec.commons.ui.base.BaseViewHolder
import hr.fer.drumre.rec.databinding.FavoritesItemShowErrorBinding
import hr.fer.drumre.rec.features.shows.favorites.ShowFavoritesViewModel

class ErrorViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<FavoritesItemShowErrorBinding>(binding = FavoritesItemShowErrorBinding.inflate(inflater)) {
    fun bind(viewModel: ShowFavoritesViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
