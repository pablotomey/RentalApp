package cl.rentalea.rentalapp.di

import cl.rentalea.rentalapp.db.AppDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistenceModule = module {
    single { AppDataBase.getInstance(androidApplication()) }
    single { get<AppDataBase>().userDao() }
    single { get<AppDataBase>().reportDao() }
    single { get<AppDataBase>().equipoDao() }
    single { get<AppDataBase>().vehiculoDao() }
}