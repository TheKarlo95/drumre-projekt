package hr.fer.drumre.rec.features.home.movies.di

import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.commons.ui.extensions.viewModel
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.features.home.movies.MoviesHomeFragment
import hr.fer.drumre.rec.features.home.movies.MoviesHomeViewModel

@Module
class MoviesHomeModule(private val fragment: MoviesHomeFragment) {

    @Provides
    @FeatureScope
    fun providesHomeViewModel() = fragment.viewModel { MoviesHomeViewModel() }
}
