package hr.fer.drumre.rec.features.shows.list.adapter.holders

import android.view.LayoutInflater
import hr.fer.drumre.rec.commons.ui.base.BaseViewHolder
import hr.fer.drumre.rec.core.network.model.Show
import hr.fer.drumre.rec.databinding.ListItemShowBinding
import hr.fer.drumre.rec.features.shows.list.ShowListViewModel

class ShowViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemShowBinding>(binding = ListItemShowBinding.inflate(inflater)) {

    fun bind(viewModel: ShowListViewModel, item: Show) {
        binding.viewModel = viewModel
        binding.show = item
        binding.executePendingBindings()
    }
}
