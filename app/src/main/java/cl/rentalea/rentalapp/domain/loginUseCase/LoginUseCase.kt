package cl.rentalea.rentalapp.domain.loginUseCase

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.Equipo
import cl.rentalea.rentalapp.db.entity.User
import cl.rentalea.rentalapp.db.entity.Vehiculo

interface LoginUseCase {

    // Consultar usuario de firebase
    suspend fun getUser(rut: String, password: String): Respuesta<User>

    // Insertar usuario
    suspend fun insertUser(user: User)

    // Obtener lista de equipos desde Firestore
    suspend fun getVehiculosFromFirestore(): Respuesta<MutableList<Vehiculo>>

    suspend fun getEquiposFromFirestore(): Respuesta<MutableList<Equipo>>

    suspend fun insertVehiculos(vehiculo: Vehiculo)

    suspend fun cleanVehiculos()

    suspend fun insertEquipos(equipo: Equipo)

    suspend fun cleanEquipos()
}