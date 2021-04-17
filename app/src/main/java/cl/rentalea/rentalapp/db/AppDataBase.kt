package cl.rentalea.rentalapp.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class AppDataBase : RoomDatabase() {

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
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
    }
}