package hr.fer.drumre.rec.features.shows.recommendations.di

import dagger.Component
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.features.shows.recommendations.ShowRecommendationFragment

@FeatureScope
@Component(
    modules = [ShowRecommendationModule::class],
    dependencies = [CoreComponent::class]
)
interface ShowRecommendationComponent {
    fun inject(listFragment: ShowRecommendationFragment)
}
