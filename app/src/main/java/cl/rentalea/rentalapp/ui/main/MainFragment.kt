package cl.rentalea.rentalapp.ui.main

import android.os.Bundle
import android.view.View
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentMainBinding
import cl.rentalea.rentalapp.utils.Constants
import cl.rentalea.rentalapp.utils.Constants.FOR_CHECKLIST_OR_SEND_REPORT
import cl.rentalea.rentalapp.utils.Constants.USER
import kotlinx.android.synthetic.main.toolbar_main.*

class MainFragment : DataBindingFragment<FragmentMainBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MainFragment
        }

        binding.user = USER

        binding.modules.reportModule.setOnClickListener {
            nav!!.navigate(R.id.action_mainFragment_to_firstReportFragment)
        }

        binding.modules.checkListModule.setOnClickListener {
            FOR_CHECKLIST_OR_SEND_REPORT = 1
            nav!!.navigate(R.id.action_mainFragment_to_reportListFragment)
        }

        binding.modules.sendReportModule.setOnClickListener {
            FOR_CHECKLIST_OR_SEND_REPORT = 2
            nav!!.navigate(R.id.action_mainFragment_to_reportListFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().btn_back.visibility = View.GONE
        requireActivity().toolbar_title.visibility = View.GONE
        requireActivity().logo_toolbar.visibility = View.VISIBLE
    }


}