package unschooler.code.ui.main.schedule

import a1.inventarization.dagger.ViewModelBuilder
import a1.inventarization.dagger.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import kotlinx.coroutines.InternalCoroutinesApi

@Module
abstract class ScheduleModule {

    @InternalCoroutinesApi
    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun scheduleFragment(): ScheduleFragment

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    abstract fun bindViewModel(viewmodel: ScheduleViewModel): ViewModel
}
