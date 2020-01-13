package hr.fer.drumre.rec.di

import dagger.Component
import hr.fer.drumre.rec.App
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.core.di.scopes.AppScope

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent {

    fun inject(application: App)
}
