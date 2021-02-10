package unschooler.code.ui.main.profile.another

import a1.inventarization.dagger.ViewModelBuilder
import a1.inventarization.dagger.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import kotlinx.coroutines.InternalCoroutinesApi
import unschooler.code.ui.main.profile.another.AnotherProfileFragment
import unschooler.code.ui.main.profile.another.AnotherProfileViewModel

@Module
abstract class AnotherProfileModule {

    @InternalCoroutinesApi
    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun anotherProfileFragment(): AnotherProfileFragment

    @Binds
    @IntoMap
    @ViewModelKey(AnotherProfileViewModel::class)
    abstract fun bindViewModel(viewmodel: AnotherProfileViewModel): ViewModel
}
