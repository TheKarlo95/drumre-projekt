package hr.fer.drumre.rec.features.shows.detail.di

import dagger.Component
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.features.shows.detail.ShowDetailFragment

@FeatureScope
@Component(
    modules = [ShowDetailModule::class],
    dependencies = [CoreComponent::class]
)
interface ShowDetailComponent {
    fun inject(detailFragment: ShowDetailFragment)
}
