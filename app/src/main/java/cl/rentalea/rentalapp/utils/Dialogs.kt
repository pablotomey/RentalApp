package cl.rentalea.rentalapp.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import cl.rentalea.rentalapp.R

fun Context.alert(op: Int, msg: String): AlertDialog {
    val icon = if (op == 0) R.drawable.ic_info else R.drawable.ic_check
    return AlertDialog.Builder(this)
            .setTitle(if (op == 0) "Alerta" else "Información")
            .setIcon(icon)
            .setMessage(msg)
            .setPositiveButton("Aceptar") { dialog, which -> dialog.dismiss()}
            .create()
}