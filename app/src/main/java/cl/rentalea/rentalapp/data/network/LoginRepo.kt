package cl.rentalea.rentalapp.data.network

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.User

interface LoginRepo {

    suspend fun getUserFromFirestore(rut: String, password: String): Respuesta<User>
}