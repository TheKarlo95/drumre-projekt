package hr.fer.drumre.rec.features.home.shows.di

import dagger.Component
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.features.home.shows.ShowsHomeFragment

@FeatureScope
@Component(
    modules = [ShowsHomeModule::class],
    dependencies = [CoreComponent::class]
)
interface ShowsHomeComponent {
    fun inject(fragment: ShowsHomeFragment)
}
