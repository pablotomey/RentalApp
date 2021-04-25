package cl.rentalea.rentalapp.model

import android.os.Parcelable
import cl.rentalea.rentalapp.model.entity.User
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(
        @SerializedName("operador") val user: User
): Parcelable