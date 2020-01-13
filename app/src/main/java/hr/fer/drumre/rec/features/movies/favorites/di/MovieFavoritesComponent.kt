package hr.fer.drumre.rec.features.movies.favorites.di

import dagger.Component
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.features.movies.favorites.MovieFavoritesFragment

@FeatureScope
@Component(
    modules = [MovieFavoritesModule::class],
    dependencies = [CoreComponent::class]
)
interface MovieFavoritesComponent {
    fun inject(fragment: MovieFavoritesFragment)
}
