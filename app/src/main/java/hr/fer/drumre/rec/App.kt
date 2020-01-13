package hr.fer.drumre.rec

import android.app.Application
import android.content.Context
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.core.di.DaggerCoreComponent
import hr.fer.drumre.rec.core.di.modules.ContextModule
import hr.fer.drumre.rec.core.utils.ThemeUtils
import hr.fer.drumre.rec.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class App : Application() {

    lateinit var coreComponent: CoreComponent

    @Inject
    lateinit var themeUtils: ThemeUtils

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initCoreDependencyInjection()
        initAppDependencyInjection()
        initNightMode()
    }

    private fun initAppDependencyInjection() {
        DaggerAppComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    private fun initCoreDependencyInjection() {
        coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initNightMode() {
        themeUtils.setNightMode(true)
    }

    companion object {
        @JvmStatic
        fun coreComponent(context: Context) = (context.applicationContext as? App)?.coreComponent
    }
}
