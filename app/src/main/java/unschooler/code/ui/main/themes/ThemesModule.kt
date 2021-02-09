package unschooler.code.ui.main.themes

import a1.inventarization.dagger.ViewModelBuilder
import a1.inventarization.dagger.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import kotlinx.coroutines.InternalCoroutinesApi

@Module
abstract class ThemesModule {

    @InternalCoroutinesApi
    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun scheduleFragment(): ThemesFragment

    @Binds
    @IntoMap
    @ViewModelKey(ThemesViewModel::class)
    abstract fun bindViewModel(viewmodel: ThemesViewModel): ViewModel
}
