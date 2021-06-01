package cl.rentalea.rentalapp.db.dao

import androidx.room.*
import cl.rentalea.rentalapp.db.entity.Report

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReport(report: Report)

    @Query("SELECT * FROM Report")
    suspend fun getAllReports(): MutableList<Report>

    @Delete
    suspend fun deleteReport(report: Report)
}