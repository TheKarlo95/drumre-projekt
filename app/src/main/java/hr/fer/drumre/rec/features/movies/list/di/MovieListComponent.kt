package hr.fer.drumre.rec.features.movies.list.di

import dagger.Component
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.features.movies.list.MovieListFragment
import hr.fer.drumre.rec.features.movies.list.di.MovieListModule

@FeatureScope
@Component(
    modules = [MovieListModule::class],
    dependencies = [CoreComponent::class]
)
interface MovieListComponent {
    fun inject(fragment: MovieListFragment)
}
