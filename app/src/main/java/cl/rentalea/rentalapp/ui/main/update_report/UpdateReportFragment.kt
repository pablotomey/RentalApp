package cl.rentalea.rentalapp.ui.main.update_report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentUpdateReportBinding
import cl.rentalea.rentalapp.utils.Constants
import org.koin.android.viewmodel.ext.android.getViewModel

class UpdateReportFragment : DataBindingFragment<FragmentUpdateReportBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_update_report

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            reportViewModel = getViewModel()
            lifecycleOwner = this@UpdateReportFragment
        }
        binding.report = Constants.REPORT!!
        binding.operator = Constants.OPERATOR!!
    }
}