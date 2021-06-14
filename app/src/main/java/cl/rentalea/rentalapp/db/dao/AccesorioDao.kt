package cl.rentalea.rentalapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import cl.rentalea.rentalapp.db.entity.Accesorio

@Dao
interface AccesorioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccesorio(accesorio: Accesorio)
}