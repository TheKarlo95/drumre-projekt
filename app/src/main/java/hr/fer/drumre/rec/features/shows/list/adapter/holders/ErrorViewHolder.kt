package hr.fer.drumre.rec.features.shows.list.adapter.holders

import android.view.LayoutInflater
import hr.fer.drumre.rec.commons.ui.base.BaseViewHolder
import hr.fer.drumre.rec.databinding.ListItemShowErrorBinding
import hr.fer.drumre.rec.features.shows.list.ShowListViewModel

class ErrorViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemShowErrorBinding>(
    binding = ListItemShowErrorBinding.inflate(inflater)
) {
    fun bind(viewModel: ShowListViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
