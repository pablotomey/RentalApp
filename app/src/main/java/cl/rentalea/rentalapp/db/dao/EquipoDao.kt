package cl.rentalea.rentalapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.rentalea.rentalapp.db.entity.Equipo

@Dao
interface EquipoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipo(equipo: Equipo)

    @Query("SELECT equipo FROM Equipo WHERE tipo_equipo = :tipoEquipo")
    suspend fun getEquiposList(tipoEquipo: String): MutableList<String>

    @Query("DELETE FROM Equipo")
    suspend fun cleanEquipos()
}