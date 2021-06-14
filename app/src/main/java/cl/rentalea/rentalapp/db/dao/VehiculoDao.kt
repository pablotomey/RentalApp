package cl.rentalea.rentalapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.rentalea.rentalapp.db.entity.Vehiculo

@Dao
interface VehiculoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehiculo(vehiculo: Vehiculo)

    @Query("SELECT patente FROM Vehiculo WHERE equipo = :equipo")
    suspend fun getPatentesList(equipo: String): MutableList<String>

    @Query("DELETE FROM Vehiculo")
    suspend fun cleanVehiculos()
}