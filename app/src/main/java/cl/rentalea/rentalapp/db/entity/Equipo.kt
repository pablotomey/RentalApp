package cl.rentalea.rentalapp.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Equipo(
    @PrimaryKey val id: Int,
    val equipo: String,
    val tipo_equipo: String
): Parcelable