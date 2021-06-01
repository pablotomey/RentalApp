package cl.rentalea.rentalapp.di

import cl.rentalea.rentalapp.data.network.LoginRepo
import cl.rentalea.rentalapp.data.network.LoginRepoImpl
import cl.rentalea.rentalapp.data.network.SendReportRepo
import cl.rentalea.rentalapp.data.network.SendReportRepoImpl
import cl.rentalea.rentalapp.db.datasource.DataSource
import cl.rentalea.rentalapp.domain.*
import org.koin.dsl.module

val repositoryModule = module { 
    single <LoginUseCase> { LoginUseCaseImpl(get(), get()) }
    single <MainRepository> { MainRepositoryImpl(get()) }
    single <LoginRepo> { LoginRepoImpl()}
    single <SendReportUseCase> { SendReportUseCaseImpl(get(), get())  }
    single <SendReportRepo> { SendReportRepoImpl()  }
    single { DataSource(get()) }
}