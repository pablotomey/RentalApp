package cl.rentalea.rentalapp.di

import cl.rentalea.rentalapp.repository.loginRepository.LoginRepository
import cl.rentalea.rentalapp.repository.loginRepository.LoginRepositoryImpl
import cl.rentalea.rentalapp.repository.reportRepository.ReportRepository
import cl.rentalea.rentalapp.repository.reportRepository.ReportRepositoryImpl
import cl.rentalea.rentalapp.db.datasource.DataSource
import cl.rentalea.rentalapp.domain.loginUseCase.LoginUseCase
import cl.rentalea.rentalapp.domain.loginUseCase.LoginUseCaseImpl
import cl.rentalea.rentalapp.domain.reportUseCase.ReportUseCase
import cl.rentalea.rentalapp.domain.reportUseCase.ReportUseCaseImpl
import org.koin.dsl.module

val repositoryModule = module { 
    single <LoginUseCase> { LoginUseCaseImpl( get() ) }
    single <LoginRepository> { LoginRepositoryImpl( get() ) }
    single <ReportUseCase> { ReportUseCaseImpl( get() )  }
    single <ReportRepository> { ReportRepositoryImpl( get() )  }
    single { DataSource(get()) }
}