package hr.fer.drumre.rec.features.movies.detail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hr.fer.drumre.rec.App.Companion.coreComponent
import hr.fer.drumre.rec.R
import hr.fer.drumre.rec.commons.ui.base.BaseFragment
import hr.fer.drumre.rec.commons.ui.extensions.observe
import hr.fer.drumre.rec.commons.views.ProgressBarDialog
import hr.fer.drumre.rec.databinding.FragmentMovieDetailBinding
import hr.fer.drumre.rec.features.movies.detail.di.DaggerMovieDetailComponent
import hr.fer.drumre.rec.features.movies.detail.di.MovieDetailModule
import javax.inject.Inject


class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>(
    layoutId = R.layout.fragment_movie_detail
) {

    @Inject
    lateinit var progressDialog: ProgressBarDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)

        val fab = viewBinding.removeFavoriteButton
        val fabDrawable: Drawable = fab.drawable
        DrawableCompat.setTint(
            fabDrawable,
            ContextCompat.getColor(requireContext(), R.color.favorite_red)
        )

        try {
            if (arguments == null) {
                viewModel.loadRandomMovie()
            } else {
                val args = MovieDetailFragmentArgs.fromBundle(arguments!!)
                viewModel.loadMovie(args.movieId)
            }
        } catch (e: IllegalStateException) {
            viewModel.loadRandomMovie()
        }
    }

    override fun onInitDependencyInjection() {
        DaggerMovieDetailComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .movieDetailModule(MovieDetailModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    private fun onViewStateChange(viewState: MovieDetailViewState) {
        when (viewState) {
            is MovieDetailViewState.Loading ->
                progressDialog.show(R.string.movie_detail_dialog_loading_text)
            is MovieDetailViewState.Error ->
                progressDialog.dismissWithErrorMessage(R.string.movie_detail_dialog_error_text)
            is MovieDetailViewState.OpenNytReview -> openUrl(viewState.url)
            is MovieDetailViewState.AddedToFavorite ->
                Snackbar.make(
                    requireView(),
                    R.string.movie_detail_added_to_favorite_message,
                    Snackbar.LENGTH_LONG
                ).show()
            is MovieDetailViewState.RemovedFromFavorite ->
                Snackbar.make(
                    requireView(),
                    R.string.movie_detail_removed_from_favorite_message,
                    Snackbar.LENGTH_LONG
                ).show()
            is MovieDetailViewState.Dismiss -> findNavController().navigateUp()
            is MovieDetailViewState.Loaded -> progressDialog.dismiss()
        }
    }

    private fun openUrl(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}
