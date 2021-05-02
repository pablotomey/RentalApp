package cl.rentalea.rentalapp.ui.main.create_report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentFirstReportBinding
import kotlinx.android.synthetic.main.toolbar_main.*

class FirstReportFragment : DataBindingFragment<FragmentFirstReportBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_first_report

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = this@FirstReportFragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().toolbar_title.text = "Ingreso de report 1ra parte"
        requireActivity().btn_back.visibility = View.VISIBLE
        requireActivity().btn_back.setOnClickListener {
            nav!!.navigate(R.id.action_firstReportFragment_to_mainFragment)
        }
    }


}