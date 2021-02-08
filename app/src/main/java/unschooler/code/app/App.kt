package unschooler.code.app

import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject


@Module
class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    @Provides
    fun provideApplication(): App = this
}