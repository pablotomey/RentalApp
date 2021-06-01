package cl.rentalea.rentalapp.domain

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.datasource.DataSource
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.db.entity.User

class MainRepositoryImpl(private val dataSource: DataSource): MainRepository {
    override suspend fun insertUser(user: User) {
        dataSource.insertUser(user)
    }

    override suspend fun insertReport(report: Report) {
        dataSource.insertReport(report)
    }

    override suspend fun getReports(): Respuesta<MutableList<Report>> {
        return dataSource.getReports()
    }
}