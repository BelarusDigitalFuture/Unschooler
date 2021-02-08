package unschooler.code.app

import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module()
class AppModule {

    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }
}