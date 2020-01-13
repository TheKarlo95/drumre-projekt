package hr.fer.drumre.rec.features.shows.favorites.di

import dagger.Component
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.features.shows.favorites.ShowFavoritesFragment

@FeatureScope
@Component(
    modules = [ShowFavoritesModule::class],
    dependencies = [CoreComponent::class]
)
interface ShowFavoritesComponent {
    fun inject(favoritesFragment: ShowFavoritesFragment)
}
