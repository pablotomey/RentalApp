package cl.rentalea.rentalapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.rentalea.rentalapp.model.entity.Report
import cl.rentalea.rentalapp.repository.MainRepository
import kotlinx.coroutines.launch

class ReportViewModel(val repo: MainRepository): ViewModel() {

    fun addReport(report: Report) {
        viewModelScope.launch {
            repo.insertReport(report)
        }
    }
}