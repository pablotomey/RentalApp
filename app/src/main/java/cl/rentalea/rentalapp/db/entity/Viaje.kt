package cl.rentalea.rentalapp.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Viaje(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "tipo_material") val material: String,
    @ColumnInfo(name = "cantidad_viajes") val cantidadViajes: Int,
    @ColumnInfo(name = "metros_cubicos_viaje") val metrosCubicos: Int,
    @ColumnInfo(name = "metros_cubicos_totales") val metrosCubicosTotales: Int,
    val report_number: Int
): Parcelable