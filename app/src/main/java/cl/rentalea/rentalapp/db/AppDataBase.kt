package cl.rentalea.rentalapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.rentalea.rentalapp.db.AppDataBase.Companion.DB_VERSION
import cl.rentalea.rentalapp.db.dao.EquipoDao
import cl.rentalea.rentalapp.db.dao.ReportDao
import cl.rentalea.rentalapp.db.dao.UserDao
import cl.rentalea.rentalapp.db.dao.VehiculoDao
import cl.rentalea.rentalapp.db.entity.Equipo
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.db.entity.User
import cl.rentalea.rentalapp.db.entity.Vehiculo

@Database(entities = [User::class, Report::class, Equipo::class, Vehiculo::class], version = DB_VERSION, exportSchema = false )
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun reportDao(): ReportDao
    abstract fun equipoDao(): EquipoDao
    abstract fun vehiculoDao(): VehiculoDao

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