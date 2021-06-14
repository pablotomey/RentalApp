package cl.rentalea.rentalapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import cl.rentalea.rentalapp.db.entity.Obra

@Dao
interface ObraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertObra(obra: Obra)
}