package cl.rentalea.rentalapp

import android.app.Application
import cl.rentalea.rentalapp.di.networkModule
import cl.rentalea.rentalapp.di.persistenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(networkModule)
            modules(persistenceModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}