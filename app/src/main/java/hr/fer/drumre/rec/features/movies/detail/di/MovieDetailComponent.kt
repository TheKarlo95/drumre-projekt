package hr.fer.drumre.rec.features.movies.detail.di

import dagger.Component
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.features.movies.detail.MovieDetailFragment

@FeatureScope
@Component(
    modules = [MovieDetailModule::class],
    dependencies = [CoreComponent::class]
)
interface MovieDetailComponent {
    fun inject(detailFragment: MovieDetailFragment)
}
