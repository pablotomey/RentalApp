package cl.rentalea.rentalapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cl.rentalea.rentalapp.db.entity.Viaje

@Dao
interface ViajeDao {

    @Insert
    suspend fun insertViaje(viaje: Viaje)

    @Query("SELECT * FROM Viaje WHERE report_number = :reportNumber")
    suspend fun getViajesList(reportNumber: Int): MutableList<Viaje>

    @Query("DELETE FROM Viaje WHERE report_number = :reportNumber")
    suspend fun deleteViaje(reportNumber: Int)

    @Query("DELETE FROM Viaje")
    suspend fun clean()
}