package cl.rentalea.rentalapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.domain.SendReportUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SendReportViewModel(private val sendReportRepository: SendReportUseCase): ViewModel() {

    val isLoading = MutableLiveData(false)
    val hasError = MutableLiveData<String>()

    fun sendReport(report: Report) = liveData(Dispatchers.IO) {
        emit(Respuesta.Loading())
        try {
            emit(sendReportRepository.sendReport(report))
        } catch (e: Exception) {
            Respuesta.Failure(e.message!!)
        }
    }

    fun deleteReport(report: Report) {
        viewModelScope.launch {
            sendReportRepository.deleteReport(report)
        }
    }
}