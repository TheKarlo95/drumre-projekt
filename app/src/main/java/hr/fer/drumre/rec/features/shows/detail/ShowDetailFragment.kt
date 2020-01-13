package hr.fer.drumre.rec.features.shows.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import hr.fer.drumre.rec.App.Companion.coreComponent
import hr.fer.drumre.rec.commons.ui.base.BaseFragment
import hr.fer.drumre.rec.commons.ui.extensions.observe
import hr.fer.drumre.rec.commons.views.ProgressBarDialog
import hr.fer.drumre.rec.R
import hr.fer.drumre.rec.databinding.FragmentShowDetailBinding
import hr.fer.drumre.rec.features.movies.detail.MovieDetailFragmentArgs
import hr.fer.drumre.rec.features.shows.detail.di.DaggerShowDetailComponent
import hr.fer.drumre.rec.features.shows.detail.di.ShowDetailModule
import javax.inject.Inject

class ShowDetailFragment : BaseFragment<FragmentShowDetailBinding, ShowDetailViewModel>(
    layoutId = R.layout.fragment_show_detail
) {

    @Inject lateinit var progressDialog: ProgressBarDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)

        try {
            if (arguments == null) {
                viewModel.loadRandomShow()
            } else {
                val args = ShowDetailFragmentArgs.fromBundle(arguments!!)
                viewModel.loadShow(args.show)
            }
        } catch (e : IllegalStateException) {
            viewModel.loadRandomShow()
        }
    }

    override fun onInitDependencyInjection() {
        DaggerShowDetailComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .showDetailModule(ShowDetailModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    private fun onViewStateChange(viewState: ShowDetailViewState) {
        when (viewState) {
            is ShowDetailViewState.Loading ->
                progressDialog.show(R.string.show_detail_dialog_loading_text)
            is ShowDetailViewState.Error ->
                progressDialog.dismissWithErrorMessage(R.string.show_detail_dialog_error_text)
            is ShowDetailViewState.AddedToFavorite ->
                Snackbar.make(
                    requireView(),
                    R.string.show_detail_added_to_favorite_message,
                    Snackbar.LENGTH_LONG
                ).show()
            is ShowDetailViewState.RemovedFromFavorite ->
                Snackbar.make(
                    requireView(),
                    R.string.show_detail_removed_from_favorite_message,
                    Snackbar.LENGTH_LONG
                ).show()
            is ShowDetailViewState.Dismiss -> findNavController().navigateUp()
            is ShowDetailViewState.Loaded -> progressDialog.dismiss()
        }
    }
}
