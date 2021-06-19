package cl.rentalea.rentalapp.viewmodel

import androidx.lifecycle.*
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

    fun obtenerObras(): LiveData<MutableList<String>> {
        val obrasList = MutableLiveData<MutableList<String>>()
        viewModelScope.launch {
            obrasList.postValue(reportRepository.getObrasList())
        }
        return obrasList
    }

    fun obtenerEmpresas(): LiveData<MutableList<String>> {
        val empresasList = MutableLiveData<MutableList<String>>()
        viewModelScope.launch {
            empresasList.postValue(reportRepository.getEmpresasList())
        }
        return empresasList
    }

    fun obtenerMateriales(): LiveData<MutableList<String>> {
        val materialesList = MutableLiveData<MutableList<String>>()
        viewModelScope.launch {
            materialesList.postValue(reportRepository.getMaterialesList())
        }
        return materialesList
    }

    fun obtenerAditamentos(): LiveData<MutableList<String>> {
        val aditamentosList = MutableLiveData<MutableList<String>>()
        viewModelScope.launch {
            aditamentosList.postValue(reportRepository.getAditamentosList())
        }
        return aditamentosList
    }

    fun obtenerAccesorios(): LiveData<MutableList<String>> {
        val accesoriosList = MutableLiveData<MutableList<String>>()
        viewModelScope.launch {
            accesoriosList.postValue(reportRepository.getAccesoriosList())
        }
        return accesoriosList
    }
}