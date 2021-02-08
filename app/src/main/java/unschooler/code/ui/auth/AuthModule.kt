package unschooler.code.ui.auth

import a1.inventarization.dagger.ViewModelBuilder
import a1.inventarization.dagger.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [
        AuthComponent::class
    ],
    includes = [
        ViewModelBuilder::class
    ]
)
abstract class AuthModule {

    @Binds
    @IntoMap
    @ClassKey(AuthActivity::class)
    internal abstract fun bindMainActivityFactory(factory: AuthComponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindViewModel(viewmodel: AuthViewModel): ViewModel
}
