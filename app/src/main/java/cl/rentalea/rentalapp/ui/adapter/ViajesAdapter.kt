package cl.rentalea.rentalapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.BaseViewHolder
import cl.rentalea.rentalapp.db.entity.Viaje
import kotlinx.android.synthetic.main.viaje_item_row.view.*

class ViajesAdapter(val context: Context, private val viajesList: MutableList<Viaje>): RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return ViajeViewHolder(LayoutInflater.from(context).inflate(R.layout.viaje_item_row, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ViajeViewHolder -> holder.bind(viajesList[position], position)
        }
    }

    override fun getItemCount(): Int = viajesList.size

    inner class ViajeViewHolder(itemView: View): BaseViewHolder<Viaje>(itemView) {
        override fun bind(item: Viaje, position: Int) {
            itemView.tipo_material.text = ("Tipo de material: ${item.material}")
            itemView.cantidad_viajes.text = ("Viajes a áridos: ${item.cantidadViajes}")
            itemView.mt_cubicos_viaje.text = ("Mt cúbicos por viaje: ${item.metrosCubicos}")
            itemView.mt_cubicos_totales.text = ("Mt cúbicos totales: ${item.metrosCubicosTotales}")
        }
    }
}