package cl.rentalea.rentalapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.rentalea.rentalapp.db.entity.CheckListItem

@Dao
interface CheckListItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckListItems(checkListItem: CheckListItem)

    @Query("SELECT * FROM CheckListItem")
    suspend fun getCheckListItems(): MutableList<CheckListItem>
}