package hr.fer.drumre.rec.features.shows.recommendations

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import hr.fer.drumre.rec.App.Companion.coreComponent
import hr.fer.drumre.rec.R
import hr.fer.drumre.rec.commons.ui.base.BaseFragment
import hr.fer.drumre.rec.commons.ui.extensions.gridLayoutManager
import hr.fer.drumre.rec.commons.ui.extensions.observe
import hr.fer.drumre.rec.core.network.model.Show
import hr.fer.drumre.rec.databinding.FragmentShowRecommendationsBinding
import hr.fer.drumre.rec.features.shows.recommendations.adapter.RecommendationAdapter
import hr.fer.drumre.rec.features.shows.recommendations.adapter.ShowAdapterState
import hr.fer.drumre.rec.features.shows.recommendations.di.DaggerShowRecommendationComponent
import hr.fer.drumre.rec.features.shows.recommendations.di.ShowRecommendationModule
import javax.inject.Inject

class ShowRecommendationFragment : BaseFragment<FragmentShowRecommendationsBinding, ShowRecommendationViewModel>(
    layoutId = R.layout.fragment_show_recommendations
) {

    @Inject lateinit var viewAdapter: RecommendationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.event, ::onViewEvent)
    }

    override fun onInitDependencyInjection() {
        DaggerShowRecommendationComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .showRecommendationModule(ShowRecommendationModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.includeRecommendations.showList.apply {
            adapter = viewAdapter
            gridLayoutManager?.spanSizeLookup = viewAdapter.getSpanSizeLookup()
        }
    }

    private fun onViewDataChange(viewData: PagedList<Show>) {
        viewAdapter.submitList(viewData)
    }

    private fun onViewStateChange(viewState: ShowRecommendationViewState) {
        when (viewState) {
            is ShowRecommendationViewState.Loaded ->
                viewAdapter.submitState(ShowAdapterState.Added)
            is ShowRecommendationViewState.AddLoading ->
                viewAdapter.submitState(ShowAdapterState.AddLoading)
            is ShowRecommendationViewState.AddError ->
                viewAdapter.submitState(ShowAdapterState.AddError)
            is ShowRecommendationViewState.NoMoreElements ->
                viewAdapter.submitState(ShowAdapterState.NoMore)
        }
    }

    private fun onViewEvent(viewEvent: ShowRecommendationViewEvent) {
        when (viewEvent) {
            is ShowRecommendationViewEvent.OpenShowDetail ->
                findNavController().navigate(
                    ShowRecommendationFragmentDirections.goToDetails(viewEvent.show.id)
                )
        }
    }
}
