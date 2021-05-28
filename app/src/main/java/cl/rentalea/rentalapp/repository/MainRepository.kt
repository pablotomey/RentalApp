package cl.rentalea.rentalapp.repository

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.model.entity.Report
import cl.rentalea.rentalapp.model.entity.User

interface MainRepository {

    suspend fun insertUser(user: User)

    suspend fun insertReport(report: Report)

    suspend fun getReports(): Respuesta<MutableList<Report>>
}