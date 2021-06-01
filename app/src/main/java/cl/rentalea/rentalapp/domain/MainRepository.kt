package cl.rentalea.rentalapp.domain

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.db.entity.User

interface MainRepository {

    suspend fun insertUser(user: User)

    suspend fun insertReport(report: Report)

    suspend fun getReports(): Respuesta<MutableList<Report>>
}