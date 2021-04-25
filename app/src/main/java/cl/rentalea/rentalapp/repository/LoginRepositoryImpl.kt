package cl.rentalea.rentalapp.repository

import cl.rentalea.rentalapp.datasource.DataSource
import cl.rentalea.rentalapp.model.entity.User

class LoginRepositoryImpl(val dataSource: DataSource): LoginRepository {
    override suspend fun insertUser(user: User) {
        dataSource.insertUser(user)
    }
}