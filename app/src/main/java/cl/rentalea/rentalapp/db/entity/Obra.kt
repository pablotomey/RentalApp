package cl.rentalea.rentalapp.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Obra(
    @PrimaryKey val id: Int,
    val obra: String
):Parcelable
