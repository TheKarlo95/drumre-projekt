package hr.fer.drumre.rec.features.shows.favorites.adapter.holders

import android.view.LayoutInflater
import hr.fer.drumre.rec.commons.ui.base.BaseViewHolder
import hr.fer.drumre.rec.core.network.model.Show
import hr.fer.drumre.rec.databinding.FavoritesItemShowBinding
import hr.fer.drumre.rec.features.shows.favorites.ShowFavoritesViewModel

class ShowViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<FavoritesItemShowBinding>(binding = FavoritesItemShowBinding.inflate(inflater)) {
    fun bind(viewModel: ShowFavoritesViewModel, item: Show) {
        binding.viewModel = viewModel
        binding.show = item
        binding.executePendingBindings()
    }
}
