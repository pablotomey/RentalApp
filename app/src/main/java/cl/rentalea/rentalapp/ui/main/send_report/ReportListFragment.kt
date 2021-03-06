package cl.rentalea.rentalapp.ui.main.send_report

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentReportListBinding
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.ui.adapter.ReportListAdapter
import cl.rentalea.rentalapp.utils.Constants
import cl.rentalea.rentalapp.utils.Constants.DLOADING
import cl.rentalea.rentalapp.utils.Constants.FOR_CHECKLIST_OR_SEND_REPORT
import cl.rentalea.rentalapp.utils.DialogLoading
import kotlinx.android.synthetic.main.toolbar_main.*
import org.koin.android.viewmodel.ext.android.getViewModel

class ReportListFragment : DataBindingFragment<FragmentReportListBinding>(), ReportListAdapter.OnReportClickListener {

    override fun getLayoutRestId(): Int = R.layout.fragment_report_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            reportViewModel = getViewModel()
            lifecycleOwner = this@ReportListFragment
        }

        //DLOADING = DialogLoading(requireContext(), "Cargando reportes")

        binding.reportViewModel?.getReports()?.observe(this, Observer { reportsResponse ->
            //binding.reportViewModel?.isLoading?.value = true
            when (reportsResponse) {
                is Respuesta.Success -> {
                    binding.reportViewModel?.isLoading?.value = false
                    if (reportsResponse.data.isNullOrEmpty()) {
                        binding.reportViewModel?.isReportVisible?.value = false
                        binding.reportViewModel?.isIconVisible?.value = true
                        binding.reportViewModel?.hasError?.value = "No tiene reportes registrados."
                    }
                    else {
                        binding.reportAdapter = ReportListAdapter(requireContext(), reportsResponse.data, this)
                        binding.reportViewModel?.isReportVisible?.value = true
                        binding.reportViewModel?.isIconVisible?.value = false
                    }

                }
                is Respuesta.Failure -> binding.reportViewModel?.hasError?.value = "No se cargaron los reportes."
            }
        })
    }

    override fun onReportClick(report: Report, position: Int) {
        Constants.REPORT = report
        when(FOR_CHECKLIST_OR_SEND_REPORT) {
            1 -> nav?.navigate(R.id.action_reportListFragment_to_checkListFragment)
            2 -> nav?.navigate(R.id.action_reportListFragment_to_sendReportFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().toolbar_title.visibility = View.VISIBLE
        requireActivity().toolbar_title.text = "Reportes por enviar"
        requireActivity().logo_toolbar.visibility = View.INVISIBLE
        requireActivity().btn_back.visibility = View.VISIBLE

        requireActivity().btn_back.setOnClickListener {
            nav!!.navigate(R.id.action_reportListFragment_to_mainFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            nav!!.navigate(R.id.action_reportListFragment_to_mainFragment)
        }
    }

}