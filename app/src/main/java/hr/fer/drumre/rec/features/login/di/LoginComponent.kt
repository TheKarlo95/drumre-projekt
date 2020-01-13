package hr.fer.drumre.rec.features.login.di

import dagger.Component
import hr.fer.drumre.rec.core.di.CoreComponent
import hr.fer.drumre.rec.core.di.scopes.FeatureScope
import hr.fer.drumre.rec.features.login.LoginFragment

@FeatureScope
@Component(
    modules = [LoginModule::class],
    dependencies = [CoreComponent::class]
)
interface LoginComponent {

    fun inject(loginFragment: LoginFragment)
}
