package cl.rentalea.rentalapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cl.rentalea.rentalapp.model.entity.Report

@Dao
interface ReportDao {

    @Insert
    suspend fun addReport(report: Report)
}