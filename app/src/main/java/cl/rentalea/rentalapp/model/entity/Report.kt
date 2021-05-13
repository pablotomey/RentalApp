package cl.rentalea.rentalapp.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Report(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val operador: String? = null,
        val date: String,
        val report_number: String,
        val equipo: String? = null,
        val tipo_equipo: String? = null,
        val patente: String,
        val obra: String? = null,
        val empresa: String? = null,
        val horometro_inicial: String,
        val horometro_final: String,
        val diferencia_horometro: String,
        val kilometraje_inicial: String,
        val kilometraje_final: String,
        val viajes_aridos: String? = null,
        val metros_cubicos_viaje: String? = null,
        val metros_cubicos_totales: String? = null,
        val litros_combustible: String,
        val horometro_combustible: String,
        val jornada: String? = null,
        val observaciones: String? = null,
        val firma_supervisor: String? = null,
        val firma_operador: String? = null
): Parcelable