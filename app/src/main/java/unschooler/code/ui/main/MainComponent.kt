package unschooler.code.ui.main

import dagger.Subcomponent
import dagger.android.AndroidInjector
import unschooler.code.ui.main.coins.CoinsModule
import unschooler.code.ui.main.profile.ProfileModule
import unschooler.code.ui.main.schedule.ScheduleModule
import unschooler.code.ui.main.teachers.TeachersModule
import javax.inject.Scope

@Subcomponent(
    modules = [
        ProfileModule::class,
        ScheduleModule::class,
        CoinsModule::class,
        TeachersModule::class
    ]
)
@MainScope
interface MainComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>
}

@Scope
@Retention
annotation class MainScope