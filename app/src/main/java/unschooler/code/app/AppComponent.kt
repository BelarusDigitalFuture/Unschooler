package unschooler.code.app

import unschooler.code.ui.main.MainModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import unschooler.code.ui.auth.AuthModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
//        AmazonBinds::class,
//        OcrBinds::class,
//        RestBinds::class,
//        LocaleBinds::class,
//        AuthModule::class,
        MainModule::class,
        AuthModule::class
//        GraphBinds::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: App): AppComponent
    }
}