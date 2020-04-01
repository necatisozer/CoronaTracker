package co.icanteach.app.coronatracker.core.inject

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
object CoroutinesModule {

    @DefaultDispatcher
    @JvmStatic
    @Provides
    internal fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @JvmStatic
    @Provides
    internal fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @JvmStatic
    @Provides
    internal fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher