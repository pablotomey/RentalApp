package cl.rentalea.rentalapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentMainBinding
import cl.rentalea.rentalapp.preferences.DataManager
import cl.rentalea.rentalapp.ui.LoginActivity
import cl.rentalea.rentalapp.utils.Constants.FOR_CHECKLIST_OR_SEND_REPORT
import kotlinx.android.synthetic.main.toolbar_main.*

class MainFragment : DataBindingFragment<FragmentMainBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_main

    private val dataManager by lazy { DataManager.getInstance(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MainFragment
        }

        binding.dataManager = DataManager.getInstance(requireContext())

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

        binding.modules.userData.closeSessionBtn.setOnClickListener {
            dataManager.logout()
            goToLoginActivity()
        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().btn_back.visibility = View.GONE
        requireActivity().toolbar_title.visibility = View.GONE
        requireActivity().logo_toolbar.visibility = View.VISIBLE
    }


}