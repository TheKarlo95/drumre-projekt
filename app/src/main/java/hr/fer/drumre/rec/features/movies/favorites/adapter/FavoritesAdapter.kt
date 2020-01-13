package hr.fer.drumre.rec.features.movies.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.fer.drumre.rec.commons.ui.base.BasePagedListAdapter
import hr.fer.drumre.rec.core.network.model.Movie
import hr.fer.drumre.rec.features.movies.favorites.MovieFavoritesViewModel
import hr.fer.drumre.rec.features.movies.favorites.adapter.holders.ErrorViewHolder
import hr.fer.drumre.rec.features.movies.favorites.adapter.holders.LoadingViewHolder
import hr.fer.drumre.rec.features.movies.favorites.adapter.holders.MovieViewHolder
import javax.inject.Inject

class FavoritesAdapter @Inject constructor(
    private val viewModel: MovieFavoritesViewModel
) : BasePagedListAdapter<Movie>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    private var state: MovieAdapterState = MovieAdapterState.Added

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (ItemView.valueOf(viewType)) {
            ItemView.MOVIE -> MovieViewHolder(inflater)
            ItemView.LOADING -> LoadingViewHolder(inflater)
            else -> ErrorViewHolder(inflater)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemView(position)) {
            ItemView.MOVIE ->
                getItem(position)?.let {
                    if (holder is MovieViewHolder) {
                        holder.bind(viewModel, it)
                    }
                }
            ItemView.ERROR ->
                if (holder is ErrorViewHolder) {
                    holder.bind(viewModel)
                }
            else -> {
            }
        }
    }

    override fun getItemId(position: Int) =
        when (getItemView(position)) {
            ItemView.MOVIE -> getItem(position)?.id ?: -1L
            ItemView.LOADING -> 0L
            ItemView.ERROR -> 1L
        }

    override fun getItemCount() =
        if (state.hasExtraRow) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }

    override fun getItemViewType(position: Int) = getItemView(position).type

    fun submitState(newState: MovieAdapterState) {
        val oldState = state
        state = newState
        if (newState.hasExtraRow && oldState != newState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    fun getSpanSizeLookup(): GridLayoutManager.SpanSizeLookup =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return getItemView(position).span
            }
        }

    internal fun getItemView(position: Int) =
        if (state.hasExtraRow && position == itemCount - 1) {
            if (state.isAddError()) {
                ItemView.ERROR
            } else {
                ItemView.LOADING
            }
        } else {
            ItemView.MOVIE
        }
}
