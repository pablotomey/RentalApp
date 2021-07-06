package cl.rentalea.rentalapp.viewmodel

import androidx.lifecycle.*
import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.db.entity.Viaje
import cl.rentalea.rentalapp.domain.reportUseCase.ReportUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReportViewModel(private val reportRepository: ReportUseCase): ViewModel() {

    val isLoading = MutableLiveData(false)
    val hasError = MutableLiveData<String>()
    val isComplete = MutableLiveData(-1)

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

    fun sendReport(report: Report, viajes: MutableList<Viaje>) {
        viewModelScope.launch {
            when (val firestorePost = reportRepository.sendReport(report, viajes)) {
                is Respuesta.Loading -> isLoading.postValue(true)
                is Respuesta.Success -> {
                    if (firestorePost.data) {
                        isLoading.postValue(false)
                        isComplete.postValue(1)
                    } else {
                        hasError.postValue("Hubo problemas al enviar el reporte, intente nuevamente")
                    }
                }
                is Respuesta.Failure -> hasError.postValue(firestorePost.exception)
            }
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

    fun addViaje(viaje: Viaje) {
        viewModelScope.launch {
            reportRepository.insertViajeData(viaje)
        }
    }

    fun obtenerViajes(reportNumber: Int) = liveData(Dispatchers.IO) {
        emit(reportRepository.getViajesList(reportNumber))
    }

    fun deleteViaje(reportNumber: Int) {
        viewModelScope.launch {
            reportRepository.deleteViaje(reportNumber)
        }
    }
}