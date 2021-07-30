package cl.rentalea.rentalapp.binding

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.db.entity.Report
import com.google.android.material.textfield.TextInputLayout

abstract class DataBindingFragment<T: ViewDataBinding> : Fragment() {
    @LayoutRes
    abstract fun getLayoutRestId(): Int

    protected lateinit var binding: T
        private set

    var nav : NavController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRestId(), container, false)
        nav = NavHostFragment.findNavController(this)
        return binding.root
    }

    open fun setTipoEquiposAdapter(tipoEquipo: AutoCompleteTextView, report: Report) {
        val items = listOf("MAQUINA", "MAQUINA CON ADITAMENTOS", "VEHICULO LIVIANO", "VEHICULO PESADO", "OTROS")
        val adapter = ArrayAdapter(requireContext(), R.layout.equipos_list_item ,items)
        (tipoEquipo).setAdapter(adapter)
        tipoEquipo.setText(report.tipo_equipo, false)
    }

    open fun setListAdapter(list: MutableList<String>, acTextView: AutoCompleteTextView) {
        val adapter = ArrayAdapter(requireContext(), R.layout.equipos_list_item ,list)
        (acTextView).setAdapter(adapter)
    }
}