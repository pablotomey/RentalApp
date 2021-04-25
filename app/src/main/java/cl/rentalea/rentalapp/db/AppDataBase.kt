package cl.rentalea.rentalapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.rentalea.rentalapp.db.AppDataBase.Companion.DB_VERSION
import cl.rentalea.rentalapp.db.dao.UserDao
import cl.rentalea.rentalapp.model.entity.User

@Database(entities = [User::class], version = DB_VERSION, exportSchema = false )
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "RENTALEA.db"
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: build(context).also { INSTANCE = it }
        }

        private fun build(context: Context) =
                Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
    }
}