package hr.fer.drumre.rec.features.shows.list.di

import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import hr.fer.drumre.rec.commons.ui.extensions.viewModel
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.core.repository.ShowRepository
import hr.fer.drumre.rec.features.shows.list.ShowListFragment
import hr.fer.drumre.rec.features.shows.list.ShowListViewModel
import hr.fer.drumre.rec.features.shows.list.adapter.ShowListAdapter
import hr.fer.drumre.rec.features.shows.list.paging.ShowPageDataSource
import hr.fer.drumre.rec.features.shows.list.paging.ShowPageDataSourceFactory

@Module
class ShowListModule(private val fragment: ShowListFragment) {

    @FeatureScope
    @Provides
    fun providesCharactersListViewModel(dataFactory: ShowPageDataSourceFactory) =
        fragment.viewModel { ShowListViewModel(dataFactory) }

    @Provides
    fun providesShowPageDataSource(
        viewModel: ShowListViewModel,
        showRepository: ShowRepository
    ) = ShowPageDataSource(
        showRepository = showRepository,
        scope = viewModel.viewModelScope
    )

    @FeatureScope
    @Provides
    fun providesCharactersListAdapter(viewModel: ShowListViewModel) = ShowListAdapter(viewModel)
}
