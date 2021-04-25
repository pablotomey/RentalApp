package cl.rentalea.rentalapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.rentalea.rentalapp.model.entity.User
import cl.rentalea.rentalapp.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository): ViewModel() {

    fun guardarUsuario(user: User) {
        viewModelScope.launch {
            loginRepository.insertUser(user)
        }
    }
}