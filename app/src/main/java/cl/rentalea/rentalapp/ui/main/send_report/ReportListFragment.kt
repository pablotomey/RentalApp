package cl.rentalea.rentalapp.ui.main.send_report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentReportListBinding
import cl.rentalea.rentalapp.model.entity.Report
import cl.rentalea.rentalapp.ui.adapter.ReportListAdapter
import kotlinx.android.synthetic.main.toolbar_main.*
import org.koin.android.viewmodel.ext.android.getViewModel

class ReportListFragment : DataBindingFragment<FragmentReportListBinding>(), ReportListAdapter.OnReportClickListener {

    override fun getLayoutRestId(): Int = R.layout.fragment_report_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = getViewModel()
            lifecycleOwner = this@ReportListFragment
        }

        binding.vm?.getReports()?.observe(this, Observer { reportsResponse ->
            when (reportsResponse) {
                is Respuesta.Loading -> binding.vm?.isLoading?.value = true
                is Respuesta.Success -> {
                    binding.vm?.isLoading?.value = false
                    if (reportsResponse.data.isNullOrEmpty()) {
                        binding.vm?.isReportVisible?.value = false
                        binding.vm?.isIconVisible?.value = true
                        binding.vm?.hasError?.value = "No tiene reportes registrados."
                    }
                    else {
                        binding.reportAdapter = ReportListAdapter(requireContext(), reportsResponse.data, this)
                        binding.vm?.isReportVisible?.value = true
                        binding.vm?.isIconVisible?.value = false
                    }

                }
                is Respuesta.Failure -> binding.vm?.hasError?.value = "No se cargaron reports."
            }
        })
    }

    override fun onReportClick(report: Report, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().toolbar_title.text = "Reportes por enviar"
        requireActivity().btn_back.visibility = View.VISIBLE
    }


}