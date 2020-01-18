package hr.fer.drumre.rec.features.movies.recommendations.di

import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.commons.ui.extensions.viewModel
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.core.repository.MovieRepository
import hr.fer.drumre.rec.features.movies.recommendations.MovieRecommendationFragment
import hr.fer.drumre.rec.features.movies.recommendations.MovieRecommendationViewModel
import hr.fer.drumre.rec.features.movies.recommendations.adapter.RecommendationAdapter
import hr.fer.drumre.rec.features.movies.recommendations.paging.MovieRecommendationDataSource
import hr.fer.drumre.rec.features.movies.recommendations.paging.MovieRecommendationDataSourceFactory

@Module
class MovieRecommendationModule(private val fragment: MovieRecommendationFragment) {

    @FeatureScope
    @Provides
    fun providesMovieRecommendationViewModel(dataFactory: MovieRecommendationDataSourceFactory) =
        fragment.viewModel { MovieRecommendationViewModel(dataFactory) }

    @Provides
    fun providesMovieRecommendationDataSource(
        viewModel: MovieRecommendationViewModel,
        movieRepository: MovieRepository
    ) = MovieRecommendationDataSource(
        movieRepository = movieRepository,
        scope = viewModel.viewModelScope
    )

    @FeatureScope
    @Provides
    fun providesRecommendationAdapter(viewModel: MovieRecommendationViewModel) = RecommendationAdapter(viewModel)
}
