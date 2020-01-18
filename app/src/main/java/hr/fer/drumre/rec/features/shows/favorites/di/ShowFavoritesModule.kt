package hr.fer.drumre.rec.features.shows.favorites.di

import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.commons.ui.extensions.viewModel
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.core.repository.ShowRepository
import hr.fer.drumre.rec.features.shows.favorites.ShowFavoritesFragment
import hr.fer.drumre.rec.features.shows.favorites.ShowFavoritesViewModel
import hr.fer.drumre.rec.features.shows.favorites.adapter.FavoritesAdapter
import hr.fer.drumre.rec.features.shows.favorites.paging.ShowFavoritesDataSource
import hr.fer.drumre.rec.features.shows.favorites.paging.ShowFavoritesDataSourceFactory

@Module
class ShowFavoritesModule(private val fragment: ShowFavoritesFragment) {

    @FeatureScope
    @Provides
    fun providesCharactersListViewModel(dataFactory: ShowFavoritesDataSourceFactory) =
        fragment.viewModel { ShowFavoritesViewModel(dataFactory) }

    @Provides
    fun providesCharactersPageDataSource(
        viewModel: ShowFavoritesViewModel,
        showRepository: ShowRepository
    ) = ShowFavoritesDataSource(
        showRepository = showRepository,
        scope = viewModel.viewModelScope
    )

    @FeatureScope
    @Provides
    fun providesCharactersListAdapter(viewModel: ShowFavoritesViewModel) = FavoritesAdapter(viewModel)
}
