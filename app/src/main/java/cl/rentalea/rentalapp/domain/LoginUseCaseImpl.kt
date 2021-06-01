package cl.rentalea.rentalapp.domain

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.data.network.LoginRepo
import cl.rentalea.rentalapp.db.datasource.DataSource
import cl.rentalea.rentalapp.db.entity.User

class LoginUseCaseImpl(
    private val dataSource: DataSource,
    private val loginRepo: LoginRepo
    ): LoginUseCase {

    override suspend fun getUser(rut: String, password: String): Respuesta<User> {
        return loginRepo.getUserFromFirestore(rut, password)
    }


    override suspend fun insertUser(user: User) {
        dataSource.insertUser(user)
    }
}