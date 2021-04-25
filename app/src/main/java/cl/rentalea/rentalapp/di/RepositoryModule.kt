package cl.rentalea.rentalapp.di

import cl.rentalea.rentalapp.repository.LoginRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module { 
    single { LoginRepositoryImpl(get()) }
}