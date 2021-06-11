package cl.rentalea.rentalapp.ui.main.send_report

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentSendReportBinding
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.utils.Constants
import cl.rentalea.rentalapp.utils.Constants.OPERATOR
import cl.rentalea.rentalapp.utils.Constants.REPORT
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
            sendReportViewModel = getViewModel()
            lifecycleOwner = this@SendReportFragment
        }
        Constants.DLOADING = DialogLoading(requireContext(), "Enviando reporte")
        Timber.e("$REPORT")
        binding.report = REPORT!!
        binding.operator = OPERATOR!!

        binding.sendBtnLayout.editBtn.setOnClickListener {
            nav?.navigate(R.id.action_sendReportFragment_to_updateReportFragment)
        }

        binding.sendBtnLayout.sendBtn.setOnClickListener {
            sendReportObserve(REPORT!!)
        }
    }

    private fun sendReportObserve(report: Report) {
        binding.sendReportViewModel?.sendReport(report)?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Respuesta.Loading -> showProgressBar(true)
                is Respuesta.Success -> {
                    if (it.data) {
                        showProgressBar(false)
                        binding.sendReportViewModel?.deleteReport(REPORT!!)
                        showSuccessDialog()
                    } else {
                        showProgressBar(false)
                        showInfoDialog("Hubo problemas al enviar el reporte, intente nuevamente")
                    }
                }
                is Respuesta.Failure -> {
                    showInfoDialog("Hubo problemas al enviar el reporte, intente nuevamente")
                }
            }
        })
    }

    private fun showProgressBar(value: Boolean) {
        binding.sendReportViewModel?.isLoading?.postValue(value)
    }

    private fun showInfoDialog(msg: String) {
        binding.sendReportViewModel?.hasError?.postValue(msg)
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