package cl.rentalea.rentalapp.domain.loginUseCase

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.data.network.loginRepository.LoginRepository
import cl.rentalea.rentalapp.db.entity.Equipo
import cl.rentalea.rentalapp.db.entity.User
import cl.rentalea.rentalapp.db.entity.Vehiculo

class LoginUseCaseImpl(private val loginRepository: LoginRepository): LoginUseCase {

    override suspend fun getUser(rut: String, password: String): Respuesta<User> {
        return loginRepository.getUserFromFirestore(rut, password)
    }

    override suspend fun insertUser(user: User) {
        loginRepository.insertUser(user)
    }

    override suspend fun getVehiculosFromFirestore(): Respuesta<MutableList<Vehiculo>> {
        return loginRepository.getVehiculosFromFirestore()
    }

    override suspend fun getEquiposFromFirestore(): Respuesta<MutableList<Equipo>> {
        return loginRepository.getEquiposFromFirestore()
    }

    override suspend fun insertVehiculos(vehiculo: Vehiculo) {
        loginRepository.insertVehiculos(vehiculo)
    }

    override suspend fun cleanVehiculos() {
        loginRepository.cleanVehiculos()
    }

    override suspend fun insertEquipos(equipo: Equipo) {
        loginRepository.insertEquipos(equipo)
    }

    override suspend fun cleanEquipos() {
        loginRepository.cleanEquipos()
    }
}