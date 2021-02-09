package unschooler.code.ui.main.themes.theme

import a1.inventarization.dagger.ViewModelBuilder
import a1.inventarization.dagger.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import kotlinx.coroutines.InternalCoroutinesApi

@Module
abstract class ThemeModule {

    @InternalCoroutinesApi
    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun scheduleFragment(): ThemeFragment

    @Binds
    @IntoMap
    @ViewModelKey(ThemeViewModel::class)
    abstract fun bindViewModel(viewmodel: ThemeViewModel): ViewModel
}
