package cl.rentalea.rentalapp.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {

    private var listener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(),listener, year, month, day)
    }

    companion object {
        fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerFragment {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.listener = listener
            return datePickerFragment
        }
    }

    fun getCurrentDayOfMonth() : Int {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

}