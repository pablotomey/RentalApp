package cl.rentalea.rentalapp.di

import cl.rentalea.rentalapp.db.AppDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistenceModule = module {
    single { AppDataBase.getInstance(androidApplication()) }
}