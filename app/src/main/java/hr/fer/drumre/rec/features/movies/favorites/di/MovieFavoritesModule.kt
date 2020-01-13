package hr.fer.drumre.rec.features.movies.favorites.di

import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.commons.ui.extensions.viewModel
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.core.network.services.MovieService
import hr.fer.drumre.rec.features.movies.favorites.MovieFavoritesFragment
import hr.fer.drumre.rec.features.movies.favorites.MovieFavoritesViewModel
import hr.fer.drumre.rec.features.movies.favorites.adapter.FavoritesAdapter
import hr.fer.drumre.rec.features.movies.favorites.paging.MovieFavoritesDataSource
import hr.fer.drumre.rec.features.movies.favorites.paging.MovieFavoritesDataSourceFactory

@Module
class MovieFavoritesModule(private val fragment: MovieFavoritesFragment) {

    @FeatureScope
    @Provides
    fun providesMovieFavoritesViewModel(dataFactory: MovieFavoritesDataSourceFactory) =
        fragment.viewModel { MovieFavoritesViewModel(dataFactory) }

    @Provides
    fun providesMovieFavoritesDataSource(
        viewModel: MovieFavoritesViewModel,
        movieService: MovieService
    ) = MovieFavoritesDataSource(
        movieService = movieService,
        scope = viewModel.viewModelScope
    )

    @FeatureScope
    @Provides
    fun providesFavoritesAdapter(viewModel: MovieFavoritesViewModel) =
        FavoritesAdapter(viewModel)
}
