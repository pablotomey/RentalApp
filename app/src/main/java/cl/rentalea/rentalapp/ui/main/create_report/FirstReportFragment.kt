package cl.rentalea.rentalapp.ui.main.create_report

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.observe
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentFirstReportBinding
import cl.rentalea.rentalapp.utils.Constants.OPERATOR
import cl.rentalea.rentalapp.utils.DatePickerFragment
import cl.rentalea.rentalapp.utils.alert
import cl.rentalea.rentalapp.utils.backToMain
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.equipos_list_item.*
import kotlinx.android.synthetic.main.fragment_first_report.*
import kotlinx.android.synthetic.main.fragment_vehiculos_report.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber
import java.util.*

class FirstReportFragment : DataBindingFragment<FragmentFirstReportBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_first_report

    companion object {
        const val OPERADOR = "operador"
        const val FECHA_REPORTE = "date_report"
        const val NUMERO_REPORTE = "numero_report"
        const val EQUIPO = "equipo_report"
        const val TIPO_EQUIPO = "tipo_equipo"
        const val PATENTE = "patente_report"
        const val NOMBRE_OBRA = "obra_report"
        const val NOMBRE_EMPRESA = "empresa_report"
        const val HOROMETRO_INICIAL = "horometro_inicial"
        const val HOROMETRO_FINAL = "horometro_final"
        const val DIFERENCIA_HOROMETRO = "diferencia_horometro"
        const val KILOMETRAJE_INICIAL = "kilometraje_inicial"
        const val KILOMETRAJE_FINAL = "kilometraje_inicial"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            reportViewModel = getViewModel()
            lifecycleOwner = this@FirstReportFragment
        }

        binding.op = OPERATOR

        val operador = binding.initDataReport.operatorName
        val date = binding.initDataReport.dateInput
        val numeroReport = binding.initDataReport.reportNumber
        val equipo = binding.vehiculoDataReport.equipoOption
        val tipoEquipo = binding.vehiculoDataReport.tipoEquipoOption
        val aditamento = binding.vehiculoDataReport.aditamentoOption
        val patente = binding.vehiculoDataReport.patenteOption
        val accesorio = binding.vehiculoDataReport.accesorioOption
        val obra = binding.empresaDataReport.obraReport
        val obraEspecifica = binding.empresaDataReport.obraSpecific
        val empresa = binding.empresaDataReport.empresaReport
        val empresaEspecifica = binding.empresaDataReport.empresaSpecific
        val horometroInicial = binding.horometroDataReport.horometroInicial
        val horometroFinal = binding.horometroDataReport.horometroFinal
        val diferenciaHorometro = binding.horometroDataReport.diferenciaHorometro
        val kilometrajeInicial = binding.horometroDataReport.kilometrajeInicial
        val kilometrajeFinal = binding.horometroDataReport.kilometrajeFinal

        val horometroFinalInput = binding.horometroDataReport.horometroFinalInput

        binding.initDataReport.dateInput.setOnClickListener {
            showDatePickerDialog()
        }

        setTipoEquiposAdapter()
        obrasListObserver()
        empresasListObserver()
        tipoEquipoSelectedListener(tipoEquipo, equipo, patente, aditamento)
        equipoSelectedListener(tipoEquipo,equipo, patente, accesorio)
        obrasSelectedListener(obra)
        empresasSelectedListener(empresa)

        diferenciaHorometroWatcher(horometroFinal, horometroInicial, diferenciaHorometro)
        enabledHorometroWatcher(horometroFinal,horometroInicial,horometroFinalInput ,diferenciaHorometro)

        binding.btnSiguiente.setOnClickListener {
            val bundle = Bundle()

            when {
                date.text.isNullOrEmpty() -> requireContext().alert(0, "Debe ingresar la fecha del report.").show()
                numeroReport.text.isNullOrEmpty() -> requireContext().alert(0, "Debe ingresar un numero de report.").show()
                patente.text.isNullOrEmpty() -> requireContext().alert(0, "Debe ingresar la patente del vehiculo.").show()
                horometroInicial.text.isNullOrEmpty() -> requireContext().alert(0, "Debe ingresar el horometro inicial.").show()
                kilometrajeInicial.text.isNullOrEmpty() -> requireContext().alert(0, "Debe ingresar el kilometraje inicial.").show()
                kilometrajeFinal.text.isNullOrEmpty() -> requireContext().alert(0, "Debe ingresar el kilometraje final.").show()

                else -> {
                    if (horometroInicial.text.toString().toInt() > horometroFinal.text.toString().toInt()) {
                        requireContext().alert(0, "El horómetro inicial no puede ser mayor que el horómetro final.").show()
                    } else {
                        bundle.putString(OPERADOR, operador.text.toString())
                        bundle.putString(FECHA_REPORTE, date.text.toString() )
                        bundle.putString(NUMERO_REPORTE, numeroReport.text.toString())
                        bundle.putString(EQUIPO, equipo.text.toString())
                        bundle.putString(TIPO_EQUIPO, tipoEquipo.text.toString())
                        bundle.putString(PATENTE, patente.text.toString())
                        bundle.putString(NOMBRE_OBRA, obra.text.toString())
                        bundle.putString(NOMBRE_EMPRESA, empresa.text.toString())
                        bundle.putString(HOROMETRO_INICIAL, horometroInicial.text.toString())
                        bundle.putString(HOROMETRO_FINAL, horometroFinal.text.toString())
                        bundle.putString(DIFERENCIA_HOROMETRO, diferenciaHorometro.text.toString())
                        bundle.putString(KILOMETRAJE_INICIAL, kilometrajeInicial.text.toString())
                        bundle.putString(KILOMETRAJE_FINAL, kilometrajeFinal.text.toString())

                        nav!!.navigate(R.id.action_firstReportFragment_to_secondReportFragment, bundle)
                    }
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePickerFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = "$day/${month+1}/$year"
            binding.initDataReport.dateInput.setText(date)
        })

        datePickerFragment.show(this.childFragmentManager, "datePicker")
    }

    private fun setTipoEquiposAdapter() {
        val items = listOf("MAQUINA", "MAQUINA CON ADITAMENTOS", "VEHICULO LIVIANO", "VEHICULO PESADO", "OTROS")
        val adapter = ArrayAdapter(requireContext(), R.layout.equipos_list_item ,items)
        (binding.vehiculoDataReport.tipoEquipoOption).setAdapter(adapter)
    }

    private fun setEquiposAdapter(tipoEquiposList: MutableList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.equipos_list_item ,tipoEquiposList)
        (binding.vehiculoDataReport.equipoOption).setAdapter(adapter)
    }

    private fun setPatentesAdapter(equiposList: MutableList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.equipos_list_item ,equiposList)
        (binding.vehiculoDataReport.patenteOption).setAdapter(adapter)
    }

    private fun setObrasAdapter(obrasList: MutableList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.equipos_list_item, obrasList)
        (binding.empresaDataReport.obraReport).setAdapter(adapter)
    }

    private fun setEmpresasAdapter(empresasList: MutableList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.equipos_list_item, empresasList)
        (binding.empresaDataReport.empresaReport).setAdapter(adapter)
    }

    private fun setAditamentosAdapter(aditamentosList: MutableList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.equipos_list_item, aditamentosList)
        (binding.vehiculoDataReport.aditamentoOption).setAdapter(adapter)
    }

    private fun setAccesoriosAdapter(accesoriosList: MutableList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.equipos_list_item, accesoriosList)
        (binding.vehiculoDataReport.accesorioOption).setAdapter(adapter)
    }

    private fun tipoEquipoSelectedListener(
        tipoEquipo: AutoCompleteTextView,
        equipo: AutoCompleteTextView,
        patente: AutoCompleteTextView,
        aditamento: AutoCompleteTextView
    ) {
        tipoEquipo.setOnItemClickListener { parent, view, position, id ->
            if (tipoEquipo.text.toString() == "MAQUINA CON ADITAMENTOS") {
                aditamentosListObserver()
                binding.vehiculoDataReport.aditamentoInput.visibility = View.VISIBLE
                equipo.setText("MINI CARGADOR")
                patente.setText("SIN PATENTE")
                equipo.isEnabled = false
                patente.isEnabled = false
            } else {
                binding.vehiculoDataReport.aditamentoInput.visibility = View.GONE
                equiposListObserver(tipoEquipo.text.toString())
                aditamento.setText("")
                equipo.isEnabled = true
                equipo.setText("")
                patente.setText("")
            }

            if (tipoEquipo.text.toString() == "VEHICULO LIVIANO" || tipoEquipo.text.toString() == "VEHICULO PESADO") {
                binding.horometroDataReport.kilometrajeInicialInput.visibility = View.VISIBLE
                binding.horometroDataReport.kilometrajeFinalInput.visibility = View.VISIBLE
            } else {
                binding.horometroDataReport.kilometrajeInicialInput.visibility = View.GONE
                binding.horometroDataReport.kilometrajeFinalInput.visibility = View.GONE
            }

            binding.vehiculoDataReport.accesorioInput.visibility = View.GONE
        }
    }

    private fun equipoSelectedListener(
        tipoEquipo: AutoCompleteTextView,
        equipo: AutoCompleteTextView,
        patente: AutoCompleteTextView,
        accesorio: AutoCompleteTextView
    ) {
        equipo.setOnItemClickListener { parent, view, position, id ->
            if (equipo.text.toString() == "TRACTOCAMION" && tipoEquipo.text.toString() == "VEHICULO PESADO" ) {
                accesoriosListObserver()
                binding.vehiculoDataReport.accesorioInput.visibility = View.VISIBLE
            } else {
                binding.vehiculoDataReport.accesorioInput.visibility = View.GONE
                accesorio.setText("")
            }
            patentesListObserver(equipo.text.toString())
            patente.isEnabled = true
            patente.setText("")
        }
    }

    private fun obrasSelectedListener(obras: AutoCompleteTextView) {
        obras.setOnItemClickListener { parent, view, position, id ->
            if (obras.text.toString() == "Otros (Especificar)") {
                binding.empresaDataReport.obraSpecificInput.visibility = View.VISIBLE
            } else {
                binding.empresaDataReport.obraSpecificInput.visibility = View.GONE
            }
        }
    }

    private fun empresasSelectedListener(empresas: AutoCompleteTextView) {
        empresas.setOnItemClickListener { parent, view, position, id ->
            if (empresas.text.toString() == "Otros (Especificar)") {
                binding.empresaDataReport.empresaSpecificInput.visibility = View.VISIBLE
            } else {
                binding.empresaDataReport.empresaSpecificInput.visibility = View.GONE
            }
        }
    }

    private fun equiposListObserver(tipoEquipo: String) {
        binding.reportViewModel?.obtenerEquipos(tipoEquipo)?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            when (response) {
                is Respuesta.Loading -> {}
                is Respuesta.Success -> {
                    setEquiposAdapter(response.data)
                }
                is Respuesta.Failure -> {}
            }
        })
    }

    private fun patentesListObserver(equipo: String) {
        binding.reportViewModel?.obtenerPatentes(equipo)?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            when (response) {
                is Respuesta.Loading -> {}
                is Respuesta.Success -> {
                    setPatentesAdapter(response.data)
                }
                is Respuesta.Failure -> {}
            }
        })
    }

    private fun obrasListObserver() {
        binding.reportViewModel?.obtenerObras()?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { obrasList ->
            setObrasAdapter(obrasList)
        })
    }

    private fun empresasListObserver() {
        binding.reportViewModel?.obtenerEmpresas()?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { empresasList ->
            setEmpresasAdapter(empresasList)
        })
    }

    private fun aditamentosListObserver() {
        binding.reportViewModel?.obtenerAditamentos()?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { aditamentosList ->
            setAditamentosAdapter(aditamentosList)
        })
    }

    private fun accesoriosListObserver() {
        binding.reportViewModel?.obtenerAccesorios()?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { accesoriosList ->
            setAccesoriosAdapter(accesoriosList)
        })
    }

    private fun diferenciaHorometroWatcher(hFinal: EditText, hInicial: EditText, dif: EditText) {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var diferencia = 0
                var horometroInicial = hInicial.text.toString()
                var horometroFinal = hFinal.text.toString()

                if (hInicial.text.isNullOrEmpty()) horometroInicial = "0"
                if (hFinal.text.isNullOrEmpty()) horometroFinal = "0"

                if (hInicial.text.isDigitsOnly() && hFinal.text.isDigitsOnly()){
                    diferencia = horometroFinal.toInt() - horometroInicial.toInt()
                    dif.setText(diferencia.toString())
                }

            }

        }

        hFinal.addTextChangedListener(textWatcher)
    }

    private fun enabledHorometroWatcher(hFinal: EditText, hInicial: EditText, inputHFinal: TextInputLayout, dif: EditText) {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var diferencia = 0
                var horometroInicial = hInicial.text.toString()
                var horometroFinal = hFinal.text.toString()

                if (hInicial.text.isNullOrEmpty()) horometroInicial = "0"
                if (hFinal.text.isNullOrEmpty()) horometroFinal = "0"

                if (horometroInicial.toInt() > 0) inputHFinal.isEnabled = true

                if (hInicial.text.isDigitsOnly() && hFinal.text.isDigitsOnly()){
                    diferencia = horometroFinal.toInt() - horometroInicial.toInt()
                    dif.setText(diferencia.toString())
                }

            }

        }

        hInicial.addTextChangedListener(textWatcher)
    }

    private fun backDialog() {
        val dialog = requireContext().backToMain("Al salir del report perderá los cambios guardados, ¿desea continuar?")
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            nav!!.navigate(R.id.action_firstReportFragment_to_mainFragment)
            arguments?.clear()
            dialog.dismiss()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().toolbar_title.visibility = View.VISIBLE
        requireActivity().logo_toolbar.visibility = View.GONE
        requireActivity().toolbar_title.text = ("Ingreso de report 1/2")
        requireActivity().btn_back.visibility = View.VISIBLE
        requireActivity().btn_back.setOnClickListener {
            backDialog()
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            backDialog()
        }
    }


}