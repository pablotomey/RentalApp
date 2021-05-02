package cl.rentalea.rentalapp.ui.main

import android.os.Bundle
import android.view.View
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.toolbar_main.*

class MainFragment : DataBindingFragment<FragmentMainBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MainFragment
        }

        binding.modules.reportModule.setOnClickListener {
            nav!!.navigate(R.id.action_mainFragment_to_firstReportFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().toolbar_title.text = ("Rental Reportes")
        requireActivity().btn_back.visibility = View.INVISIBLE
    }


}