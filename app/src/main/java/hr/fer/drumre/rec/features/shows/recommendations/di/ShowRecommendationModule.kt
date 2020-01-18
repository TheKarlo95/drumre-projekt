package hr.fer.drumre.rec.features.shows.recommendations.di

import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.commons.ui.extensions.viewModel
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.core.repository.ShowRepository
import hr.fer.drumre.rec.features.shows.recommendations.ShowRecommendationFragment
import hr.fer.drumre.rec.features.shows.recommendations.ShowRecommendationViewModel
import hr.fer.drumre.rec.features.shows.recommendations.adapter.RecommendationAdapter
import hr.fer.drumre.rec.features.shows.recommendations.paging.ShowRecommendationDataSource
import hr.fer.drumre.rec.features.shows.recommendations.paging.ShowRecommendationDataSourceFactory

@Module
class ShowRecommendationModule(private val fragment: ShowRecommendationFragment) {

    @FeatureScope
    @Provides
    fun providesShowRecommendationViewModel(dataFactory: ShowRecommendationDataSourceFactory) =
        fragment.viewModel { ShowRecommendationViewModel(dataFactory) }

    @Provides
    fun providesShowRecommendationDataSource(
        viewModel: ShowRecommendationViewModel,
        showRepository: ShowRepository
    ) = ShowRecommendationDataSource(
        showRepository = showRepository,
        scope = viewModel.viewModelScope
    )

    @FeatureScope
    @Provides
    fun providesRecommendationAdapter(viewModel: ShowRecommendationViewModel) = RecommendationAdapter(viewModel)
}
