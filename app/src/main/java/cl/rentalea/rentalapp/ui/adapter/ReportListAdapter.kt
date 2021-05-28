package cl.rentalea.rentalapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.BaseViewHolder
import cl.rentalea.rentalapp.model.entity.Report
import cl.rentalea.rentalapp.utils.Constants.REPORT
import kotlinx.android.synthetic.main.report_item_row.view.*

class ReportListAdapter(val context: Context, private val reportList: MutableList<Report>, val reportClickListener: OnReportClickListener): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnReportClickListener {
        fun onReportClick(report: Report, position: Int)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<*> {
        return ReportViewHolder(LayoutInflater.from(context).inflate(R.layout.report_item_row, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ReportViewHolder -> holder.bind(reportList[position], position)
        }
    }

    override fun getItemCount(): Int = reportList.size

    inner class ReportViewHolder(itemView: View): BaseViewHolder<Report>(itemView) {
        override fun bind(item: Report, position: Int) {
            itemView.n_report.text = ("N° de Report: ${item.report_number}")
            itemView.date_report.text = ("Fecha: ${item.date}")
            itemView.patente_report.text = ("Patente vehicular: ${item.patente}")

            itemView.report_row.setOnClickListener {
                reportClickListener.onReportClick(item, position)
                REPORT = reportList[position]
            }
        }
    }
}