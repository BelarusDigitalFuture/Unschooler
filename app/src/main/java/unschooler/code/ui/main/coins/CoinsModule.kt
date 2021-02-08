package unschooler.code.ui.main.coins

import a1.inventarization.dagger.ViewModelBuilder
import a1.inventarization.dagger.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import kotlinx.coroutines.InternalCoroutinesApi

@Module
abstract class CoinsModule {

    @InternalCoroutinesApi
    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun coinsFragment(): CoinsFragment

    @Binds
    @IntoMap
    @ViewModelKey(CoinsViewModel::class)
    abstract fun bindViewModel(viewmodel: CoinsViewModel): ViewModel
}
