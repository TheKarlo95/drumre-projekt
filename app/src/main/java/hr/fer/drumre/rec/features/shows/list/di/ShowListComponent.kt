package hr.fer.drumre.rec.features.shows.list.di

import dagger.Component
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.features.shows.list.ShowListFragment

@FeatureScope
@Component(
    modules = [ShowListModule::class],
    dependencies = [CoreComponent::class]
)
interface ShowListComponent {
    fun inject(listFragment: ShowListFragment)
}
