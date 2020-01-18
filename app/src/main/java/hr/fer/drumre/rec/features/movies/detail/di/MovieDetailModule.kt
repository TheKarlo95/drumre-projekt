package hr.fer.drumre.rec.features.movies.detail.di

import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.commons.ui.extensions.viewModel
import hr.fer.drumre.rec.commons.views.ProgressBarDialog
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.core.repository.MovieRepository
import hr.fer.drumre.rec.features.movies.detail.MovieDetailFragment
import hr.fer.drumre.rec.features.movies.detail.MovieDetailViewModel

@Module
class MovieDetailModule(private val fragment: MovieDetailFragment) {

    @FeatureScope
    @Provides
    fun providesCharacterDetailViewModel(
        movieRepository: MovieRepository
    ) = fragment.viewModel { MovieDetailViewModel(movieRepository = movieRepository) }

    @FeatureScope
    @Provides
    fun providesProgressBarDialog() = ProgressBarDialog(fragment.requireContext())
}
