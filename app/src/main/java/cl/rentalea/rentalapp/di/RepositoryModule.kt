package cl.rentalea.rentalapp.di

import cl.rentalea.rentalapp.datasource.DataSource
import cl.rentalea.rentalapp.repository.LoginRepositoryImpl
import cl.rentalea.rentalapp.repository.MainRepository
import cl.rentalea.rentalapp.repository.MainRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module { 
    single { LoginRepositoryImpl(get()) }
    single <MainRepository> { MainRepositoryImpl(get()) }
    single { DataSource(get()) }
}