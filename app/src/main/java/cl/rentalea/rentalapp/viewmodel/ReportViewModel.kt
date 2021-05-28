package cl.rentalea.rentalapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.model.entity.Report
import cl.rentalea.rentalapp.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReportViewModel(private val repo: MainRepository): ViewModel() {

    val isLoading = MutableLiveData<Boolean>(false)
    val isComplete = MutableLiveData(-1)
    val hasError = MutableLiveData<String>()
    val isReportVisible = MutableLiveData<Boolean>(false)
    val isIconVisible = MutableLiveData<Boolean>(false)

    fun addReport(report: Report) {
        viewModelScope.launch {
            repo.insertReport(report)
        }
    }

    fun getReports() = liveData(Dispatchers.IO) {
        emit(Respuesta.Loading())
        try {
            emit(repo.getReports())
        } catch (e: Exception) {
            emit(Respuesta.Failure(e.message!!))
        }
    }
}