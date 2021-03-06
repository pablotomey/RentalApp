package cl.rentalea.rentalapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.rentalea.rentalapp.db.entity.Aditamento

@Dao
interface AditamientoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAditamento(aditamento: Aditamento)

    @Query("SELECT aditamento FROM Aditamento")
    suspend fun getAditamentos(): MutableList<String>
}