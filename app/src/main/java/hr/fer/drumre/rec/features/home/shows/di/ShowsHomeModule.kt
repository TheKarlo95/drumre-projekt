package hr.fer.drumre.rec.features.home.shows.di

import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.commons.ui.extensions.viewModel
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.features.home.shows.ShowsHomeFragment
import hr.fer.drumre.rec.features.home.shows.ShowsHomeViewModel

@Module
class ShowsHomeModule(private val fragment: ShowsHomeFragment) {

    @Provides
    @FeatureScope
    fun providesHomeViewModel() = fragment.viewModel { ShowsHomeViewModel() }
}
