package cl.rentalea.rentalapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.rentalea.rentalapp.db.AppDataBase.Companion.DB_VERSION
import cl.rentalea.rentalapp.db.dao.*
import cl.rentalea.rentalapp.db.entity.*

@Database(
    entities = [
        User::class,
        Report::class,
        Equipo::class,
        Vehiculo::class,
        Obra::class,
        Empresa::class,
        Material::class,
        Aditamento::class,
        Accesorio::class,
        Viaje::class,
        CheckListItem::class
    ], version = DB_VERSION, exportSchema = false )
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun reportDao(): ReportDao
    abstract fun equipoDao(): EquipoDao
    abstract fun vehiculoDao(): VehiculoDao
    abstract fun obraDao(): ObraDao
    abstract fun empresaDao(): EmpresaDao
    abstract fun materialDao(): MaterialDao
    abstract fun aditamentoDao(): AditamientoDao
    abstract fun accesorioDao(): AccesorioDao
    abstract fun viajeDao(): ViajeDao
    abstract fun checkListItemDao(): CheckListItemDao

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