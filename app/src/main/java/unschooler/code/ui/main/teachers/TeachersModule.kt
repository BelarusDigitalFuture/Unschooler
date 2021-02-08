package unschooler.code.ui.main.teachers

import a1.inventarization.dagger.ViewModelBuilder
import a1.inventarization.dagger.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import kotlinx.coroutines.InternalCoroutinesApi

@Module
abstract class TeachersModule {

    @InternalCoroutinesApi
    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun teachersFragment(): TeachersFragment

    @Binds
    @IntoMap
    @ViewModelKey(TeachersViewModel::class)
    abstract fun bindViewModel(viewmodel: TeachersViewModel): ViewModel
}
