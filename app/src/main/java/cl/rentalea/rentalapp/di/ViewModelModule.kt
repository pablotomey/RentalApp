package cl.rentalea.rentalapp.di

import cl.rentalea.rentalapp.viewmodel.LoginViewModel
import cl.rentalea.rentalapp.viewmodel.ReportViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module { 
    viewModel { LoginViewModel(get()) }
    viewModel { ReportViewModel() }
}