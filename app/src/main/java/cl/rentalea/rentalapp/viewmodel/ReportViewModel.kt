package cl.rentalea.rentalapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.domain.reportUseCase.ReportUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReportViewModel(private val reportRepository: ReportUseCase): ViewModel() {

    val isLoading = MutableLiveData(false)
    val hasError = MutableLiveData<String>()

    val isReportVisible = MutableLiveData<Boolean>(false)
    val isIconVisible = MutableLiveData<Boolean>(false)

    fun addReport(report: Report) {
        viewModelScope.launch {
            reportRepository.insertReport(report)
        }
    }

    fun getReports() = liveData(Dispatchers.IO) {
        emit(Respuesta.Loading())
        try {
            emit(reportRepository.getReports())
        } catch (e: Exception) {
            emit(Respuesta.Failure(e.message!!))
        }
    }

    fun sendReport(report: Report) = liveData(Dispatchers.IO) {
        emit(Respuesta.Loading())
        try {
            emit(reportRepository.sendReport(report))
        } catch (e: Exception) {
            Respuesta.Failure(e.message!!)
        }
    }

    fun deleteReport(report: Report) {
        viewModelScope.launch {
            reportRepository.deleteReport(report)
        }
    }

    fun obtenerEquipos(tipoEquipo: String) = liveData(Dispatchers.IO) {
        emit(Respuesta.Loading())
        try {
            emit(reportRepository.getEquiposList(tipoEquipo))
        } catch (e: Exception) {
            Respuesta.Failure(e.message!!)
        }
    }

    fun obtenerPatentes(equipo: String) = liveData(Dispatchers.IO) {
        emit(Respuesta.Loading())
        try {
            emit(reportRepository.getPatentesList(equipo))
        } catch (e: Exception) {
            Respuesta.Failure(e.message!!)
        }
    }
}