package cl.rentalea.rentalapp.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class CheckListItem(
    @PrimaryKey val id: Int,
    val item_name: String,
    var status: Int
): Parcelable