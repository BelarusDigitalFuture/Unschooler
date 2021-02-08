package unschooler.code.ui.main

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
        MainComponent::class
    ],
    includes = [
        ViewModelBuilder::class
    ]
)
abstract class MainModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    internal abstract fun bindMainActivityFactory(factory: MainComponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModel(viewmodel: MainViewModel): ViewModel
}
