package cl.rentalea.rentalapp.datasource

import cl.rentalea.rentalapp.db.AppDataBase
import cl.rentalea.rentalapp.model.entity.Report
import cl.rentalea.rentalapp.model.entity.User

class DataSource(private val roomDataBase: AppDataBase) {

    suspend fun insertUser(user: User) {
        roomDataBase.userDao().saveUser(user)
    }

    suspend fun insertReport(report: Report) {
        roomDataBase.reportDao().addReport(report)
    }
}