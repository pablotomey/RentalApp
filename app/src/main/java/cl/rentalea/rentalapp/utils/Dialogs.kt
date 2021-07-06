package cl.rentalea.rentalapp.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import cl.rentalea.rentalapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.viajes_dialog_layout.view.*

fun Context.alert(op: Int, msg: String): AlertDialog {
    val icon = if (op == 0) R.drawable.ic_info else R.drawable.ic_check
    return AlertDialog.Builder(this)
            .setTitle(if (op == 0) "Alerta" else "Información")
            .setIcon(icon)
            .setMessage(msg)
            .setPositiveButton("Aceptar") { dialog, which -> dialog.dismiss()}
            .setCancelable(false)
            .create()
}

fun Context.backToMain(msg: String): AlertDialog {
    val icon = R.drawable.ic_info
    return AlertDialog.Builder(this)
        .setTitle("Información")
        .setIcon(icon)
        .setMessage(msg)
        .setPositiveButton("Aceptar", null)
        .setNegativeButton("Cancelar") { dialog, which -> dialog.dismiss() }
        .setCancelable(false)
        .create()
}

fun Context.addViaje(viajesList: MutableList<String>): MaterialAlertDialogBuilder {
    val layout = LayoutInflater.from(this).inflate(R.layout.viajes_dialog_layout, null, false)
    val adapter = ArrayAdapter(this, R.layout.equipos_list_item, viajesList)
    layout.tipo_material_option.setAdapter(adapter)
    return MaterialAlertDialogBuilder(this).setView(layout)
        .setCancelable(true)
        .setPositiveButton("Agregar", null)
}