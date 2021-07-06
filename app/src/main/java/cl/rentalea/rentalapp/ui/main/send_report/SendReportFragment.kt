package cl.rentalea.rentalapp.ui.main.send_report

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentSendReportBinding
import cl.rentalea.rentalapp.ui.adapter.ViajesAdapter
import cl.rentalea.rentalapp.utils.Constants
import cl.rentalea.rentalapp.utils.Constants.REPORT
import cl.rentalea.rentalapp.utils.Constants.USER
import cl.rentalea.rentalapp.utils.Constants.VIAJES
import cl.rentalea.rentalapp.utils.DialogLoading
import cl.rentalea.rentalapp.utils.alert
import kotlinx.android.synthetic.main.toolbar_main.*
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber

class SendReportFragment : DataBindingFragment<FragmentSendReportBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_send_report

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            reportViewModel = getViewModel()
            lifecycleOwner = this@SendReportFragment
        }
        Constants.DLOADING = DialogLoading(requireContext(), "Enviando reporte")
        Timber.e("$REPORT")
        binding.report = REPORT!!
        binding.user = USER!!

        binding.sendBtnLayout.editBtn.setOnClickListener {
            nav?.navigate(R.id.action_sendReportFragment_to_updateReportFragment)
        }

        binding.sendBtnLayout.sendBtn.setOnClickListener {
            binding.reportViewModel?.sendReport(REPORT!!, VIAJES!!)
        }

        getViajesListObserve(REPORT!!.report_number)
        isCompleteObserve()
    }

    private fun isCompleteObserve() {
        binding.reportViewModel?.isComplete?.observe(viewLifecycleOwner, Observer { isComplete ->
            if (isComplete == 1) {
                binding.reportViewModel?.deleteReport(REPORT!!)
                binding.reportViewModel?.deleteViaje(REPORT!!.report_number)
                showSuccessDialog()
            }
        })
    }

    private fun getViajesListObserve(reportNumber: Int) {
        binding.reportViewModel?.obtenerViajes(reportNumber)?.observe(viewLifecycleOwner, Observer { viajesList ->
            VIAJES = viajesList
            binding.viajesLayout.viajesAdapter = ViajesAdapter(requireContext(), viajesList)
            binding.viajesLayout.viajesAdapter!!.notifyDataSetChanged()
        })
    }

    private fun showSuccessDialog() {
        val dialog = requireContext().alert(1, "Reporte enviado con exito.")
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            nav?.navigate(R.id.action_sendReportFragment_to_reportListFragment)
            dialog.dismiss()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().btn_back.setOnClickListener {
            nav!!.navigate(R.id.action_sendReportFragment_to_reportListFragment)
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            nav!!.navigate(R.id.action_sendReportFragment_to_reportListFragment)
        }
    }

}