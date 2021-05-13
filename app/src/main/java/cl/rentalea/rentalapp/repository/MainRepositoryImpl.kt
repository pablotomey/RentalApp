package cl.rentalea.rentalapp.repository

import cl.rentalea.rentalapp.datasource.DataSource
import cl.rentalea.rentalapp.model.entity.Report
import cl.rentalea.rentalapp.model.entity.User

class MainRepositoryImpl(private val dataSource: DataSource): MainRepository {
    override suspend fun insertUser(user: User) {
        dataSource.insertUser(user)
    }

    override suspend fun insertReport(report: Report) {
        dataSource.insertReport(report)
    }
}