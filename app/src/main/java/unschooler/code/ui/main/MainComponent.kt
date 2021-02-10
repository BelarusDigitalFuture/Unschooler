package unschooler.code.ui.main

import dagger.Subcomponent
import dagger.android.AndroidInjector
import unschooler.code.ui.main.coins.CoinsModule
import unschooler.code.ui.main.profile.ProfileModule
import unschooler.code.ui.main.profile.another.AnotherProfileModule
import unschooler.code.ui.main.profile.progress.ProgressModule
import unschooler.code.ui.main.themes.ThemesModule
import unschooler.code.ui.main.teachers.TeachersModule
import unschooler.code.ui.main.themes.theme.ThemeModule
import javax.inject.Scope

@Subcomponent(
    modules = [
        ProfileModule::class,
        ThemesModule::class,
        CoinsModule::class,
        TeachersModule::class,
        ThemeModule::class,
        AnotherProfileModule::class,
        ProgressModule::class
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