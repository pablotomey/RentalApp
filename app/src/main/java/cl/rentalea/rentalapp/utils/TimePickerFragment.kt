package cl.rentalea.rentalapp.utils

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment() {
    private var listener: TimePickerDialog.OnTimeSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        val st = calendar.get(Calendar.AM_PM)
        return TimePickerDialog(requireContext(), listener, hour, min, true)
    }

    companion object {
        fun newInstance(listener: TimePickerDialog.OnTimeSetListener): TimePickerFragment {
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.listener = listener
            return timePickerFragment
        }
    }
}