package cl.rentalea.rentalapp.repository

import cl.rentalea.rentalapp.model.entity.User

interface LoginRepository {

    // Insertar usuario
    suspend fun insertUser(user: User)
}