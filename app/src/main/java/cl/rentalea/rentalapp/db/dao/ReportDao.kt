package cl.rentalea.rentalapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.rentalea.rentalapp.model.entity.Report

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReport(report: Report)

    @Query("SELECT * FROM Report")
    suspend fun getAllReports(): MutableList<Report>
}