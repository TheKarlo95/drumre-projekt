package hr.fer.drumre.rec.features.movies.list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import hr.fer.drumre.rec.App.Companion.coreComponent
import hr.fer.drumre.rec.MainActivity
import hr.fer.drumre.rec.R
import hr.fer.drumre.rec.commons.ui.base.BaseFragment
import hr.fer.drumre.rec.commons.ui.extensions.gridLayoutManager
import hr.fer.drumre.rec.commons.ui.extensions.observe
import hr.fer.drumre.rec.core.network.model.Movie
import hr.fer.drumre.rec.databinding.FragmentMovieListBinding
import hr.fer.drumre.rec.features.movies.list.adapter.MovieAdapterState
import hr.fer.drumre.rec.features.movies.list.adapter.MovieListAdapter
import hr.fer.drumre.rec.features.movies.list.di.DaggerMovieListComponent
import hr.fer.drumre.rec.features.movies.list.di.MovieListModule
import javax.inject.Inject

class MovieListFragment : BaseFragment<FragmentMovieListBinding, MovieListViewModel>(
    layoutId = R.layout.fragment_movie_list
) {

    @Inject lateinit var viewAdapter: MovieListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.event, ::onViewEvent)
        subscribeToSearch()
    }

    override fun onInitDependencyInjection() {
        DaggerMovieListComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .movieListModule(MovieListModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.includeList.movieList.apply {
            adapter = viewAdapter
            gridLayoutManager?.spanSizeLookup = viewAdapter.getSpanSizeLookup()
        }
    }

    private fun onViewDataChange(viewData: PagedList<Movie>) {
        viewAdapter.submitList(viewData)
    }

    private fun onViewStateChange(viewState: MovieListViewState) {
        when (viewState) {
            is MovieListViewState.Loaded ->
                viewAdapter.submitState(MovieAdapterState.Added)
            is MovieListViewState.AddLoading ->
                viewAdapter.submitState(MovieAdapterState.AddLoading)
            is MovieListViewState.AddError ->
                viewAdapter.submitState(MovieAdapterState.AddError)
            is MovieListViewState.NoMoreElements ->
                viewAdapter.submitState(MovieAdapterState.NoMore)
        }
    }

    private fun onViewEvent(viewEvent: MovieListViewEvent) {
        when (viewEvent) {
            is MovieListViewEvent.OpenMovieDetail ->
                findNavController().navigate(
                    MovieListFragmentDirections.goToDetails(viewEvent.movie)
                )
        }
    }

    private fun subscribeToSearch() {
        val mainActivity = activity as MainActivity
        if (mainActivity.onQueryListeners["MovieListFragment"] == null) {
            mainActivity.onQueryListeners["MovieListFragment"] = { viewModel.changeQuery(it) }
        }
    }

    private fun unsubscribeToSearch() {
        val mainActivity = activity as MainActivity
        mainActivity.onQueryListeners.remove("MovieListFragment")
    }
}
