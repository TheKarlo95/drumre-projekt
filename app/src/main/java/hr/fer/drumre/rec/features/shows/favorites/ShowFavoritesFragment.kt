package hr.fer.drumre.rec.features.shows.favorites

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
import hr.fer.drumre.rec.databinding.FragmentShowFavoritesBinding
import hr.fer.drumre.rec.features.shows.favorites.adapter.FavoritesAdapter
import hr.fer.drumre.rec.features.shows.favorites.adapter.ShowAdapterState
import hr.fer.drumre.rec.features.shows.favorites.di.DaggerShowFavoritesComponent
import hr.fer.drumre.rec.features.shows.favorites.di.ShowFavoritesModule
import javax.inject.Inject

class ShowFavoritesFragment : BaseFragment<FragmentShowFavoritesBinding, ShowFavoritesViewModel>(
    layoutId = R.layout.fragment_show_favorites
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
        DaggerShowFavoritesComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .showFavoritesModule(ShowFavoritesModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.includeFavorites.showList.apply {
            adapter = viewAdapter
            gridLayoutManager?.spanSizeLookup = viewAdapter.getSpanSizeLookup()
        }
    }

    private fun onViewDataChange(viewData: PagedList<Show>) {
        viewAdapter.submitList(viewData)
    }

    private fun onViewStateChange(viewState: ShowFavoritesViewState) {
        when (viewState) {
            is ShowFavoritesViewState.Loaded ->
                viewAdapter.submitState(ShowAdapterState.Added)
            is ShowFavoritesViewState.AddLoading ->
                viewAdapter.submitState(ShowAdapterState.AddLoading)
            is ShowFavoritesViewState.AddError ->
                viewAdapter.submitState(ShowAdapterState.AddError)
            is ShowFavoritesViewState.NoMoreElements ->
                viewAdapter.submitState(ShowAdapterState.NoMore)
        }
    }

    private fun onViewEvent(viewEvent: ShowFavoritesViewEvent) {
        when (viewEvent) {
            is ShowFavoritesViewEvent.OpenShowDetail ->
                findNavController().navigate(
                    ShowFavoritesFragmentDirections.goToDetails(viewEvent.show.id)
                )
        }
    }
}
