package cl.rentalea.rentalapp.repository

import cl.rentalea.rentalapp.model.entity.Report
import cl.rentalea.rentalapp.model.entity.User

interface MainRepository {

    suspend fun insertUser(user: User)

    suspend fun insertReport(report: Report)
}