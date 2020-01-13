package hr.fer.drumre.rec.features.movies.recommendations

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
import hr.fer.drumre.rec.databinding.FragmentMovieRecommendationsBinding
import hr.fer.drumre.rec.features.movies.recommendations.adapter.MovieAdapterState
import hr.fer.drumre.rec.features.movies.recommendations.adapter.RecommendationAdapter
import hr.fer.drumre.rec.features.movies.recommendations.di.DaggerMovieRecommendationComponent
import hr.fer.drumre.rec.features.movies.recommendations.di.MovieRecommendationModule
import javax.inject.Inject

class MovieRecommendationFragment : BaseFragment<FragmentMovieRecommendationsBinding, MovieRecommendationViewModel>(
    layoutId = R.layout.fragment_movie_recommendations
) {

    @Inject lateinit var viewAdapter: RecommendationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.event, ::onViewEvent)
    }

    override fun onInitDependencyInjection() {
        DaggerMovieRecommendationComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .movieRecommendationModule(MovieRecommendationModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.includeRecommendations.movieList.apply {
            adapter = viewAdapter
            gridLayoutManager?.spanSizeLookup = viewAdapter.getSpanSizeLookup()
        }
    }

    private fun onViewDataChange(viewData: PagedList<Movie>) {
        viewAdapter.submitList(viewData)
    }

    private fun onViewStateChange(viewState: MovieRecommendationViewState) {
        when (viewState) {
            is MovieRecommendationViewState.Loaded ->
                viewAdapter.submitState(MovieAdapterState.Added)
            is MovieRecommendationViewState.AddLoading ->
                viewAdapter.submitState(MovieAdapterState.AddLoading)
            is MovieRecommendationViewState.AddError ->
                viewAdapter.submitState(MovieAdapterState.AddError)
            is MovieRecommendationViewState.NoMoreElements ->
                viewAdapter.submitState(MovieAdapterState.NoMore)
        }
    }

    private fun onViewEvent(viewEvent: MovieRecommendationViewEvent) {
        when (viewEvent) {
            is MovieRecommendationViewEvent.OpenMovieDetail ->
                findNavController().navigate(
                    MovieRecommendationFragmentDirections.goToDetails(viewEvent.movie)
                )
        }
    }
}
