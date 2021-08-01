package cl.rentalea.rentalapp.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.BaseViewHolder
import cl.rentalea.rentalapp.db.entity.CheckListItem
import kotlinx.android.synthetic.main.checklist_item_row.view.*

class CheckListadapter(val context: Context, private val itemList: MutableList<CheckListItem>, val itemClickListener: OnItemClickListener): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnItemClickListener {
        fun onItemClick(item: CheckListItem, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.checklist_item_row, parent,false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(itemList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ItemViewHolder(itemView: View): BaseViewHolder<CheckListItem>(itemView) {
        override fun bind(item: CheckListItem, position: Int) {

            itemView.item_name.text = item.item_name
            itemView.item_state.text = when (item.status) {
                0 -> "Sin revisar"
                1 -> "Bueno"
                2 -> "Regular"
                3 -> "Malo"
                else -> "No aplica"
            }
            itemView.item_state.setTextColor( when(item.status) {
                1 -> ContextCompat.getColor(context, R.color.stroke_green)
                2 -> ContextCompat.getColor(context, R.color.stroke_orange)
                3 -> ContextCompat.getColor(context, R.color.btn_red)
                4 -> ContextCompat.getColor(context, R.color.blue_light)
                else -> ContextCompat.getColor(context, R.color.dark_status)
            })
            itemView.item_img.setImageResource(if (item.status == 0) R.drawable.ic_alert else R.drawable.ic_check_ok)

            itemView.item_row.setOnClickListener {
                itemClickListener.onItemClick(item, position)
            }
        }
    }
}