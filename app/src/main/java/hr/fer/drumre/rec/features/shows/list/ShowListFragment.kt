package hr.fer.drumre.rec.features.shows.list

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
import hr.fer.drumre.rec.core.network.model.Show
import hr.fer.drumre.rec.databinding.FragmentShowListBinding
import hr.fer.drumre.rec.features.shows.list.adapter.ShowAdapterState
import hr.fer.drumre.rec.features.shows.list.adapter.ShowListAdapter
import hr.fer.drumre.rec.features.shows.list.di.DaggerShowListComponent
import hr.fer.drumre.rec.features.shows.list.di.ShowListModule
import javax.inject.Inject

class ShowListFragment : BaseFragment<FragmentShowListBinding, ShowListViewModel>(
    layoutId = R.layout.fragment_show_list
) {

    @Inject lateinit var viewAdapter: ShowListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.event, ::onViewEvent)
        subscribeToSearch()
    }

    override fun onInitDependencyInjection() {
        DaggerShowListComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .showListModule(ShowListModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.includeList.showList.apply {
            adapter = viewAdapter
            gridLayoutManager?.spanSizeLookup = viewAdapter.getSpanSizeLookup()
        }
    }

    private fun onViewDataChange(viewData: PagedList<Show>) {
        viewAdapter.submitList(viewData)
    }

    private fun onViewStateChange(viewState: ShowListViewState) {
        when (viewState) {
            is ShowListViewState.Loaded ->
                viewAdapter.submitState(ShowAdapterState.Added)
            is ShowListViewState.AddLoading ->
                viewAdapter.submitState(ShowAdapterState.AddLoading)
            is ShowListViewState.AddError ->
                viewAdapter.submitState(ShowAdapterState.AddError)
            is ShowListViewState.NoMoreElements ->
                viewAdapter.submitState(ShowAdapterState.NoMore)
        }
    }

    private fun onViewEvent(viewEvent: ShowListViewEvent) {
        when (viewEvent) {
            is ShowListViewEvent.OpenShowDetail ->
                findNavController().navigate(
                    ShowListFragmentDirections.goToDetails(viewEvent.show)
                )
        }
    }

    private fun subscribeToSearch() {
        val mainActivity = activity as MainActivity
        if (mainActivity.onQueryListeners["ShowListFragment"] == null) {
            mainActivity.onQueryListeners["ShowListFragment"] = { viewModel.changeQuery(it) }
        }
    }

    private fun unsubscribeToSearch() {
        val mainActivity = activity as MainActivity
        mainActivity.onQueryListeners.remove("ShowListFragment")
    }
}
