package cl.rentalea.rentalapp.ui.main.create_report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentCheckListBinding
import cl.rentalea.rentalapp.db.entity.CheckListItem
import cl.rentalea.rentalapp.ui.adapter.CheckListadapter
import cl.rentalea.rentalapp.ui.adapter.ReportListAdapter
import cl.rentalea.rentalapp.utils.Constants.CHECKLIST_ITEM
import cl.rentalea.rentalapp.utils.itemStatusDialog
import kotlinx.android.synthetic.main.fragment_check_list.*
import kotlinx.android.synthetic.main.item_status_dialog_layout.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class CheckListFragment : DataBindingFragment<FragmentCheckListBinding>() , CheckListadapter.OnItemClickListener {

    override fun getLayoutRestId(): Int = R.layout.fragment_check_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            reportViewModel = getViewModel()
            lifecycleOwner = this@CheckListFragment
        }

        binding.reportViewModel?.obtenerCheckItemsList()?.observe(viewLifecycleOwner, Observer { checkItemsList ->
            if (checkItemsList != null) {
                binding.checkListAdapter = CheckListadapter(requireContext(), checkItemsList, this)
                itemListRecycler.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            }
        })
    }

    override fun onItemClick(item: CheckListItem, position: Int) {
        CHECKLIST_ITEM = item
        val dialog = requireContext().itemStatusDialog().show()
        dialog.get_status_btn.setOnClickListener {
            when(dialog.status_group.checkedRadioButtonId) {
                R.id.status_bueno -> {
                    updateCheckListItem(CHECKLIST_ITEM!!, 1)
                    dialog.dismiss()
                }
                R.id.status_regular -> {
                    updateCheckListItem(CHECKLIST_ITEM!!, 2)
                    dialog.dismiss()
                }
                R.id.status_malo -> {
                    updateCheckListItem(CHECKLIST_ITEM!!, 3)
                    dialog.dismiss()
                }
                R.id.status_no_aplica -> {
                    updateCheckListItem(CHECKLIST_ITEM!!, 4)
                    dialog.dismiss()
                }
                else -> {
                    dialog.error_msg.visibility = View.VISIBLE
                    dialog.error_msg.text = "Debe seleccionar un estado."
                }
            }
        }
    }

    private fun updateCheckListItem(checkListItem: CheckListItem, status: Int) {
        checkListItem.status = status
        Timber.e("CHECK LIST ITEM $checkListItem")
        binding.reportViewModel?.actualizarCheckListItem(checkListItem)
        binding.checkListAdapter?.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().toolbar_title.visibility = View.VISIBLE
        requireActivity().toolbar_title.text = "Check list"
        requireActivity().logo_toolbar.visibility = View.INVISIBLE
        requireActivity().btn_back.visibility = View.VISIBLE

        requireActivity().btn_back.setOnClickListener {
            nav!!.navigate(R.id.action_checkListFragment_to_mainFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            nav!!.navigate(R.id.action_checkListFragment_to_mainFragment)
        }
    }
}