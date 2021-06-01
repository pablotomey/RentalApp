package cl.rentalea.rentalapp

import android.app.Application
import cl.rentalea.rentalapp.di.persistenceModule
import cl.rentalea.rentalapp.di.repositoryModule
import cl.rentalea.rentalapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@Suppress("Unused")
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(persistenceModule)
            modules(repositoryModule)
            modules(viewModelModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}