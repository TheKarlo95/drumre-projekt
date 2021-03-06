package hr.fer.drumre.rec

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.facebook.AccessToken
import com.google.android.material.navigation.NavigationView
import hr.fer.drumre.rec.core.utils.ThemeUtilsImpl
import hr.fer.drumre.rec.core.utils.UserPreferencesImpl
import hr.fer.drumre.rec.features.login.menu.ToggleThemeCheckBox
import hr.fer.drumre.rec.features.splash.SplashActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val themeUtils by lazy { ThemeUtilsImpl() }
    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    private val navigationView by lazy { findViewById<NavigationView>(R.id.navigation_view) }
    private val navController: NavController
        get() = findNavController(R.id.nav_host_fragment)

    var onQueryListeners: MutableMap<String, (query: String) -> Unit> = hashMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeDrawer()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        menu.findItem(R.id.menu_toggle_theme).apply {
            val actionView = this.actionView
            if (actionView is ToggleThemeCheckBox) {
                actionView.isChecked = themeUtils.isDarkTheme(this@MainActivity)
                actionView.setOnCheckedChangeListener { _, isChecked ->
                    themeUtils.setNightMode(isChecked, DELAY_TO_APPLY_THEME)
                }
            }
        }
        menu.findItem(R.id.menu_search).apply {
            val actionView = this.actionView
            if (actionView is SearchView) {
                actionView.setOnQueryTextListener(DebouncedOnQueryTextListener {query ->
                    onQueryListeners.values.forEach { listener ->
                        listener(query)
                    }
                })
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_movies -> navController.navigate(R.id.movies_home)
            R.id.nav_shows -> navController.navigate(R.id.shows_home)
            R.id.nav_logout -> logout()
            else -> return false
        }

        item.isChecked = true
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                } else {
                    false
                }
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    private fun initializeDrawer() {
        val navController = navController
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun logout() {
        val userPreferences = UserPreferencesImpl(this)
        userPreferences.set(null, null)
        AccessToken.setCurrentAccessToken(null)
        SplashActivity.startWith(this)
    }

    companion object {
        private const val DELAY_TO_APPLY_THEME = 150L

        fun startWith(context: Context) {
            val intent = Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            context.startActivity(intent)
        }
    }
}
