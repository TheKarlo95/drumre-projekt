package hr.fer.drumre.rec.features.home.movies.di

import dagger.Component
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.features.home.movies.MoviesHomeFragment

@FeatureScope
@Component(
    modules = [MoviesHomeModule::class],
    dependencies = [CoreComponent::class]
)
interface MoviesHomeComponent {
    fun inject(fragment: MoviesHomeFragment)
}
