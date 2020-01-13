package hr.fer.drumre.rec.features.movies.favorites

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import hr.fer.drumre.rec.App.Companion.coreComponent
import hr.fer.drumre.rec.commons.ui.base.BaseFragment
import hr.fer.drumre.rec.commons.ui.extensions.observe
import hr.fer.drumre.rec.commons.ui.extensions.gridLayoutManager
import hr.fer.drumre.rec.core.network.model.Movie
import hr.fer.drumre.rec.R
import hr.fer.drumre.rec.databinding.FragmentMovieFavoritesBinding
import hr.fer.drumre.rec.features.movies.favorites.adapter.FavoritesAdapter
import hr.fer.drumre.rec.features.movies.favorites.adapter.MovieAdapterState
import hr.fer.drumre.rec.features.movies.favorites.di.DaggerMovieFavoritesComponent
import hr.fer.drumre.rec.features.movies.favorites.di.MovieFavoritesModule
import javax.inject.Inject

class MovieFavoritesFragment : BaseFragment<FragmentMovieFavoritesBinding, MovieFavoritesViewModel>(
    layoutId = R.layout.fragment_movie_favorites
) {

    @Inject
    lateinit var viewAdapter: FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.event, ::onViewEvent)
    }

    override fun onInitDependencyInjection() {
        DaggerMovieFavoritesComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .movieFavoritesModule(MovieFavoritesModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.includeFavorites.movieList.apply {
            adapter = viewAdapter
            gridLayoutManager?.spanSizeLookup = viewAdapter.getSpanSizeLookup()
        }
    }

    private fun onViewDataChange(viewData: PagedList<Movie>) {
        viewAdapter.submitList(viewData)
    }

    private fun onViewStateChange(viewState: MovieFavoritesViewState) {
        when (viewState) {
            is MovieFavoritesViewState.Loaded ->
                viewAdapter.submitState(MovieAdapterState.Added)
            is MovieFavoritesViewState.AddLoading ->
                viewAdapter.submitState(MovieAdapterState.AddLoading)
            is MovieFavoritesViewState.AddError ->
                viewAdapter.submitState(MovieAdapterState.AddError)
            is MovieFavoritesViewState.NoMoreElements ->
                viewAdapter.submitState(MovieAdapterState.NoMore)
        }
    }

    private fun onViewEvent(viewEvent: MovieFavoritesViewEvent) {
        when (viewEvent) {
            is MovieFavoritesViewEvent.OpenMovieDetail ->
                findNavController().navigate(
                    MovieFavoritesFragmentDirections.goToDetails(viewEvent.movie)
                )
        }
    }
}
