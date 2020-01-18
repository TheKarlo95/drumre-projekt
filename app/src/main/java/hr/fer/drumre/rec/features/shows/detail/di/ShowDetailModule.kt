package hr.fer.drumre.rec.features.shows.detail.di

import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.commons.ui.extensions.viewModel
import hr.fer.drumre.rec.commons.views.ProgressBarDialog
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.core.repository.ShowRepository
import hr.fer.drumre.rec.features.shows.detail.ShowDetailFragment
import hr.fer.drumre.rec.features.shows.detail.ShowDetailViewModel

@Module
class ShowDetailModule(private val fragment: ShowDetailFragment) {

    @FeatureScope
    @Provides
    fun providesShowDetailViewModel(
        showRepository: ShowRepository
    ) = fragment.viewModel { ShowDetailViewModel(showRepository = showRepository) }

    @FeatureScope
    @Provides
    fun providesProgressBarDialog() = ProgressBarDialog(fragment.requireContext())
}
