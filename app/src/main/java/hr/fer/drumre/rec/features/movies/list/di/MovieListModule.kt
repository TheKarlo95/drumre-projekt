package hr.fer.drumre.rec.features.movies.list.di

import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.commons.ui.extensions.viewModel
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.core.repository.MovieRepository
import hr.fer.drumre.rec.features.movies.list.MovieListFragment
import hr.fer.drumre.rec.features.movies.list.MovieListViewModel
import hr.fer.drumre.rec.features.movies.list.adapter.MovieListAdapter
import hr.fer.drumre.rec.features.movies.list.paging.MoviePageDataSource
import hr.fer.drumre.rec.features.movies.list.paging.MoviePageDataSourceFactory

@Module
class MovieListModule(private val fragment: MovieListFragment) {

    @FeatureScope
    @Provides
    fun providesMovieListViewModel(dataFactory: MoviePageDataSourceFactory) =
        fragment.viewModel { MovieListViewModel(dataFactory) }

    @Provides
    fun providesMoviePageDataSource(
        viewModel: MovieListViewModel,
        movieRepository: MovieRepository
    ) = MoviePageDataSource(
        movieRepository = movieRepository,
        scope = viewModel.viewModelScope
    )

    @FeatureScope
    @Provides
    fun providesMovieListAdapter(viewModel: MovieListViewModel) = MovieListAdapter(viewModel)
}
