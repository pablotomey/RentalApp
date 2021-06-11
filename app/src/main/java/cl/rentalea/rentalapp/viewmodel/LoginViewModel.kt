package cl.rentalea.rentalapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.Equipo
import cl.rentalea.rentalapp.db.entity.User
import cl.rentalea.rentalapp.db.entity.Vehiculo
import cl.rentalea.rentalapp.domain.loginUseCase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginUseCase): ViewModel() {

    val isLoading = MutableLiveData(false)
    val hasError = MutableLiveData<String>()

    fun obtenerUsuario(rut: String, password: String) = liveData(Dispatchers.IO) {
        emit(Respuesta.Loading())
        try {
            emit(loginRepository.getUser(rut, password))
        } catch (e: Exception) {
            emit(Respuesta.Failure(e.message.toString()))
        }
    }

    fun guardarUsuario(user: User) {
        viewModelScope.launch {
            loginRepository.insertUser(user)
        }
    }

    fun guardarVehiculo(vehiculo: Vehiculo) {
        viewModelScope.launch {
            loginRepository.insertVehiculos(vehiculo)
        }
    }

    fun eliminarVehiculos() {
        viewModelScope.launch {
            loginRepository.cleanVehiculos()
        }
    }

    fun guardarEquipo(equipo: Equipo) {
        viewModelScope.launch {
            loginRepository.insertEquipos(equipo)
        }
    }

    fun eliminarEquipos() {
        viewModelScope.launch {
            loginRepository.cleanEquipos()
        }
    }

    fun obtenerListaVehiculos() = liveData(Dispatchers.IO) {
        emit(Respuesta.Loading())
        try {
            emit(loginRepository.getVehiculosFromFirestore())
        } catch (e: Exception) {
            emit(Respuesta.Failure(e.message!!))
        }
    }

    fun obtenerListaEquipos() = liveData(Dispatchers.IO) {
        emit(Respuesta.Loading())
        try {
            emit(loginRepository.getEquiposFromFirestore())
        } catch (e: Exception) {
            emit(Respuesta.Failure(e.message!!))
        }
    }
}