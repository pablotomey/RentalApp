package cl.rentalea.rentalapp.domain

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.User

interface LoginUseCase {

    // consultar usuario de firebase
    suspend fun getUser(rut: String, password: String): Respuesta<User>

    // Insertar usuario
    suspend fun insertUser(user: User)
}