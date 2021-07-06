package cl.rentalea.rentalapp.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Report(
        @PrimaryKey val report_number: Int,
        val operador: String? = null,
        val date: String,
        val equipo: String? = null,
        val equipo_arrastre: String? = null,
        val tipo_equipo: String? = null,
        val patente: String,
        val aditamento: String? = null,
        val obra: String? = null,
        val empresa: String? = null,
        val horometro_inicial: String,
        val horometro_final: String,
        val diferencia_horometro: String,
        val kilometraje_inicial: String,
        val kilometraje_final: String,
        val litros_combustible: String,
        val horometro_combustible: String,
        val inicio_jornada: String? = null,
        val fin_jornada: String? = null,
        val observaciones: String? = null,
        val firma_supervisor: String? = null,
        val firma_operador: String? = null
): Parcelable