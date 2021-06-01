package cl.rentalea.rentalapp.db.datasource

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.AppDataBase
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.db.entity.User

class DataSource(private val roomDataBase: AppDataBase) {

    suspend fun insertUser(user: User) {
        roomDataBase.userDao().saveUser(user)
    }

    suspend fun insertReport(report: Report) {
        roomDataBase.reportDao().addReport(report)
    }

    suspend fun getReports(): Respuesta<MutableList<Report>> {
        return Respuesta.Success(roomDataBase.reportDao().getAllReports())
    }

    suspend fun deleteReport(report: Report) {
        roomDataBase.reportDao().deleteReport(report)
    }
}