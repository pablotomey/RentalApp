package cl.rentalea.rentalapp.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = false) val id: Int = 1,
    @ColumnInfo(name = "operator_name") val nombre: String,
    @ColumnInfo(name = "dni") val rut: String,
    @ColumnInfo(name = "position") val cargo: String
) : Parcelable