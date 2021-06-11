package cl.rentalea.rentalapp.data.network.loginRepository

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.Equipo
import cl.rentalea.rentalapp.db.entity.User
import cl.rentalea.rentalapp.db.entity.Vehiculo

interface LoginRepository {

    suspend fun insertUser(user: User)

    suspend fun insertVehiculos(vehiculo: Vehiculo)

    suspend fun cleanVehiculos()

    suspend fun insertEquipos(equipo: Equipo)

    suspend fun cleanEquipos()

    suspend fun getUserFromFirestore(rut: String, password: String): Respuesta<User>

    suspend fun getVehiculosFromFirestore(): Respuesta<MutableList<Vehiculo>>

    suspend fun getEquiposFromFirestore(): Respuesta<MutableList<Equipo>>
}