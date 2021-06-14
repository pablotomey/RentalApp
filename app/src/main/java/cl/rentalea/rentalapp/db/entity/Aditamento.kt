package cl.rentalea.rentalapp.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Aditamento(
    @PrimaryKey val id: Int,
    val aditamento: String
): Parcelable
