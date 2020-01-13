package hr.fer.drumre.rec.features.movies.recommendations.di

import dagger.Component
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.features.movies.recommendations.MovieRecommendationFragment

@FeatureScope
@Component(
    modules = [MovieRecommendationModule::class],
    dependencies = [CoreComponent::class]
)
interface MovieRecommendationComponent {
    fun inject(listFragment: MovieRecommendationFragment)
}
