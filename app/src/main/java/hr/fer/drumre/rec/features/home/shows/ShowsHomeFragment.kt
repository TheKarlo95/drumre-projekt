package hr.fer.drumre.rec.features.home.shows

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import hr.fer.drumre.rec.App
import hr.fer.drumre.rec.commons.ui.base.BaseFragment
import hr.fer.drumre.rec.commons.ui.extensions.setupWithNavController
import hr.fer.drumre.rec.core.utils.ThemeUtils
import hr.fer.drumre.rec.R
import hr.fer.drumre.rec.databinding.FragmentShowsHomeBinding
import hr.fer.drumre.rec.features.home.shows.di.DaggerShowsHomeComponent
import hr.fer.drumre.rec.features.home.shows.di.ShowsHomeModule
import hr.fer.drumre.rec.features.login.menu.ToggleThemeCheckBox
import javax.inject.Inject

class ShowsHomeFragment : BaseFragment<FragmentShowsHomeBinding, ShowsHomeViewModel>(
    layoutId = R.layout.fragment_shows_home
) {

    @Inject
    lateinit var themeUtils: ThemeUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            setupShowsBottomNavigationBar()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupShowsBottomNavigationBar()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)

        menu.findItem(R.id.menu_toggle_theme).apply {
            val actionView = this.actionView
            if (actionView is ToggleThemeCheckBox) {
                actionView.isChecked = themeUtils.isDarkTheme(requireContext())
                actionView.setOnCheckedChangeListener { _, isChecked ->
                    themeUtils.setNightMode(isChecked,
                        DELAY_TO_APPLY_THEME
                    )
                }
            }
        }
    }

    override fun onInitDependencyInjection() {
        DaggerShowsHomeComponent
            .builder()
            .coreComponent(App.coreComponent(requireContext()))
            .showsHomeModule(ShowsHomeModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    private fun setupShowsBottomNavigationBar() {
        val navController = viewBinding.bottomNavigation.setupWithNavController(
            navGraphIds = SHOW_GRAPHS_IDS,
            fragmentManager = childFragmentManager,
            containerId = R.id.nav_host_container,
            intent = requireActivity().intent
        )

        navController.observe(viewLifecycleOwner, Observer {
            viewModel.navigationControllerChanged(it)
        })
    }

    companion object {
        private const val DELAY_TO_APPLY_THEME = 250L
        @JvmStatic private val SHOW_GRAPHS_IDS = listOf(
            R.navigation.navigation_show_list_graph,
            R.navigation.navigation_show_recommendations_graph,
            R.navigation.navigation_show_favorites_graph,
            R.navigation.navigation_show_random_graph
        )
    }
}
