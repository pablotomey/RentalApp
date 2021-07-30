package cl.rentalea.rentalapp.ui.main.update_report

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentUpdateReportBinding
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.utils.*
import cl.rentalea.rentalapp.utils.Constants.REPORT
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.toolbar_main.*
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber

class UpdateReportFragment : DataBindingFragment<FragmentUpdateReportBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_update_report

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            reportViewModel = getViewModel()
            lifecycleOwner = this@UpdateReportFragment
        }
        binding.report = REPORT!!
        binding.user = Constants.USER!!
        binding.equipoOption.setText(REPORT!!.equipo, false)
        binding.aditamentoOption.setText(REPORT!!.aditamento, false)
        binding.patenteOption.setText(REPORT!!.patente, false)
        binding.equipoArrastreOption.setText(REPORT!!.equipo_arrastre, false)
        binding.obraOption.setText(REPORT!!.obra, false)

        if (REPORT!!.firma_supervisor == "Si") binding.firmaSi.isChecked = true
        else binding.firmaNo.isChecked = true

        when(binding.aditamentoOption.text.toString()) {
            "Sin aditamento" -> binding.aditamentoInput.isEnabled = false
            else -> {
                binding.aditamentoInput.isEnabled = true
                binding.equipoInput.isEnabled = false
                binding.patenteInput.isEnabled = false
                aditamentosListObserver()

            }
        }

        when(binding.equipoArrastreOption.text.toString()) {
            "Sin equipo de arrastre" -> binding.equipoArrastreInput.isEnabled = false
            else -> binding.equipoArrastreInput.isEnabled = true
        }

        // Function from DataBindingFragment
        setTipoEquiposAdapter(binding.tipoEquipoOption, REPORT!!)
        tipoEquipoSelectedListener()
        equipoSelectedListener()
        obrasSelectedListener()
        empresasSelectedListener()

        equiposListObserver(binding.tipoEquipoOption.text.toString())
        patentesListObserver(binding.equipoOption.text.toString())
        accesoriosListObserver()
        obrasListObserver()
        empresasListObserver()

        diferenciaHorometroWatcher()
        enabledHorometroWatcher()

        if (binding.tipoEquipoOption.text.toString() == "VEHICULO LIVIANO" || binding.tipoEquipoOption.text.toString() == "VEHICULO PESADO") {
            binding.kilometrajeInicialInput.isEnabled = true
            binding.kilometrajeFinalInput.isEnabled = true
        } else {
            binding.kilometrajeInicialInput.isEnabled = false
            binding.kilometrajeFinalInput.isEnabled = false
        }

        binding.inicioJornada.setOnClickListener {
            showTimePickerDialog(0)
        }

        binding.finJornada.setOnClickListener {
            showTimePickerDialog(1)
        }

        binding.cancelOrSaveLayout.editViajesBtn.setOnClickListener {
            // TODO: 12-07-2021 go to edit viajes fragment
        }

        binding.cancelOrSaveLayout.saveBtn.setOnClickListener {

            val error = when {
                binding.tipoEquipoOption.text.isNullOrEmpty() -> "Debe selecionar un tipo de equipo."
                binding.equipoOption.text.isNullOrEmpty() -> "Debe seleccionar un vehiculo."
                binding.aditamentoOption.text.isNullOrEmpty() -> "Debe seleccionar un aditamento."
                binding.patenteOption.text.isNullOrEmpty() -> "Debe seleccionar una patente para el vehiculo."
                binding.equipoArrastreOption.text.isNullOrEmpty() -> "Debe seleccionar un equipo de arrastre."
                binding.obraOption.text.isNullOrEmpty() -> "Debe seleccionar una obra."
                (binding.obraSpecificInput.isEnabled && binding.obraSpecific.text.isNullOrEmpty()) -> "Debe ingresar una obra."
                binding.empresaOption.text.isNullOrEmpty() -> "Debe seleccionar una empresa."
                (binding.empresaSpecificInput.isEnabled && binding.empresaSpecific.text.isNullOrEmpty()) -> "Debe ingresar una empresa."
                binding.horometroInicial.text.isNullOrEmpty() -> "Debe ingresar el horómetro inicial."
                binding.horometroFinal.text.isNullOrEmpty() -> "Debe ingresar el horómetro final."
                (binding.kilometrajeInicialInput.isEnabled && binding.kilometrajeInicial.text.toString() == "0" || binding.kilometrajeInicial.text.isNullOrEmpty() ) -> "Debe ingresar el kilometraje inicial."
                (binding.kilometrajeFinalInput.isEnabled && binding.kilometrajeFinal.text.toString() == "0" || binding.kilometrajeFinal.text.isNullOrEmpty() ) -> "Debe ingresar el kilometraje final."
                else -> null
            }

            if (!error.isNullOrEmpty()) requireContext().alert(0, error).show()
            else {
                if (binding.horometroInicial.text.toString().toInt() > binding.horometroFinal.text.toString().toInt()) {
                    requireContext().alert(0, "El horómetro inicial no puede ser mayor al horómetro final.").show()
                } else {
                    val firmaSupervisor = if (binding.firmaSi.isChecked) "Si" else "No"

                    val report = Report(
                        REPORT!!.report_number,
                        REPORT!!.operador,
                        REPORT!!.date,
                        binding.equipoOption.text.toString(),
                        binding.equipoArrastreOption.text.toString(),
                        binding.tipoEquipoOption.text.toString(),
                        binding.patenteOption.text.toString(),
                        binding.aditamentoOption.text.toString(),
                        if (binding.obraOption.text.toString() == "Otra (especificar)") binding.obraSpecific.text.toString() else binding.obraOption.text.toString(),
                        if (binding.empresaOption.text.toString() == "Otra (especificar)") binding.empresaSpecific.text.toString() else binding.empresaOption.text.toString(),
                        binding.horometroInicial.text.toString(),
                        binding.horometroFinal.text.toString(),
                        binding.diferenciaHorometro.text.toString(),
                        binding.kilometrajeInicial.text.toString(),
                        binding.kilometrajeFinal.text.toString(),
                        binding.cantidadCombustible.text.toString(),
                        binding.horometroCombustible.text.toString(),
                        binding.inicioJornada.text.toString(),
                        binding.finJornada.text.toString(),
                        binding.observaciones.text.toString(),
                        firmaSupervisor,
                        REPORT!!.firma_operador
                    )
                    REPORT = report
                    binding.reportViewModel?.updateReport(report)

                    updateReportDialog("Reporte actualizado!")
                }
            }
        }

        binding.cancelOrSaveLayout.cancelBtn.setOnClickListener {
            backDialog()
        }
    }

    private fun equiposListObserver(tipoEquipo: String) {
        binding.reportViewModel?.obtenerEquipos(tipoEquipo)?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { equiposList ->
            setListAdapter(equiposList, binding.equipoOption)
        })
    }

    private fun aditamentosListObserver() {
        binding.reportViewModel?.obtenerAditamentos()?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { aditamentosList ->
            setListAdapter(aditamentosList, binding.aditamentoOption)
        })
    }

    private fun patentesListObserver(equipo: String) {
        binding.reportViewModel?.obtenerPatentes(equipo)?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { patentesList ->
            setListAdapter(patentesList, binding.patenteOption)
        })
    }

    private fun accesoriosListObserver() {
        binding.reportViewModel?.obtenerAccesorios()?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { accesoriosList ->
            setListAdapter(accesoriosList, binding.equipoArrastreOption)
        })
    }

    private fun obrasListObserver() {
        binding.reportViewModel?.obtenerObras()?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { obrasList ->
            setListAdapter(obrasList, binding.obraOption)
        })
    }

    private fun empresasListObserver() {
        binding.reportViewModel?.obtenerEmpresas()?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { empresasList ->
            setListAdapter(empresasList, binding.empresaOption)
        })
    }

    private fun tipoEquipoSelectedListener() {
        binding.tipoEquipoOption.setOnItemClickListener { parent, view, position, id ->
            if (binding.tipoEquipoOption.text.toString() == "MAQUINA CON ADITAMENTOS") {
                aditamentosListObserver()
                binding.equipoOption.setText("MINI CARGADOR")
                binding.patenteOption.setText("SIN PATENTE")
                binding.aditamentoOption.setText("")
                binding.aditamentoInput.isEnabled = true
                binding.equipoInput.isEnabled = false
                binding.patenteInput.isEnabled = false
            } else {
                equiposListObserver(binding.tipoEquipoOption.text.toString())
                binding.aditamentoInput.isEnabled = false
                binding.equipoInput.isEnabled = true
                binding.patenteInput.isEnabled = false
                binding.aditamentoOption.setText("Sin aditamento")
                binding.equipoArrastreOption.setText("Sin equipo de arrastre")
                binding.equipoOption.setText("")
                binding.patenteOption.setText("")
            }

            if (binding.tipoEquipoOption.text.toString() == "VEHICULO LIVIANO" || binding.tipoEquipoOption.text.toString() == "VEHICULO PESADO") {
                binding.kilometrajeInicialInput.isEnabled = true
                binding.kilometrajeFinalInput.isEnabled = true
            } else {
                binding.kilometrajeInicialInput.isEnabled = false
                binding.kilometrajeFinalInput.isEnabled = false
                binding.kilometrajeInicial.setText("0")
                binding.kilometrajeFinal.setText("0")
            }

            binding.equipoArrastreInput.isEnabled = false
        }
    }

    private fun equipoSelectedListener() {
        binding.equipoOption.setOnItemClickListener { parent, view, position, id ->
            if (binding.equipoOption.text.toString() == "TRACTOCAMION" && binding.tipoEquipoOption.text.toString() == "VEHICULO PESADO" ) {
                accesoriosListObserver()
                binding.equipoArrastreInput.isEnabled = true
                binding.equipoArrastreOption.setText("")
            } else {
                binding.equipoArrastreInput.isEnabled = false
                binding.equipoArrastreOption.setText("Sin equipo de arrastre")
            }
            patentesListObserver(binding.equipoOption.text.toString())
            binding.patenteInput.isEnabled = true
            binding.patenteOption.setText("")
        }
    }

    private fun obrasSelectedListener() {
        binding.obraOption.setOnItemClickListener { parent, view, position, id ->
            binding.obraSpecificInput.isEnabled = binding.obraOption.text.toString() == "Otra (especificar)"
        }
    }

    private fun empresasSelectedListener() {
        binding.empresaOption.setOnItemClickListener { parent, view, position, id ->
            binding.empresaSpecificInput.isEnabled = binding.empresaOption.text.toString() == "Otra (especificar)"
        }
    }

    private fun diferenciaHorometroWatcher() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var diferencia = 0
                var horometroInicial = binding.horometroInicial.text.toString()
                var horometroFinal = binding.horometroFinal.text.toString()

                if (binding.horometroInicial.text.isNullOrEmpty()) horometroInicial = "0"
                if (binding.horometroFinal.text.isNullOrEmpty()) horometroFinal = "0"

                if (binding.horometroInicial.text!!.isDigitsOnly() && binding.horometroFinal.text!!.isDigitsOnly()){
                    diferencia = horometroFinal.toInt() - horometroInicial.toInt()
                    binding.diferenciaHorometro.setText(diferencia.toString())
                }

            }

        }

        binding.horometroFinal.addTextChangedListener(textWatcher)
    }

    private fun enabledHorometroWatcher() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var diferencia = 0
                var horometroInicial = binding.horometroInicial.text.toString()
                var horometroFinal = binding.horometroFinal.text.toString()

                if (binding.horometroInicial.text.isNullOrEmpty()) horometroInicial = "0"
                if (binding.horometroFinal.text.isNullOrEmpty()) horometroFinal = "0"

                if (horometroInicial.toInt() > 0) binding.horometroFinalInput.isEnabled = true

                if (binding.horometroInicial.text!!.isDigitsOnly() && binding.horometroFinal.text!!.isDigitsOnly()){
                    diferencia = horometroFinal.toInt() - horometroInicial.toInt()
                    binding.diferenciaHorometro.setText(diferencia.toString())
                }
            }
        }
        binding.horometroInicial.addTextChangedListener(textWatcher)
    }

    private fun showTimePickerDialog(op: Int) {
        val timePickerFragment = TimePickerFragment.newInstance(TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val hour = if (hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"
            val min = if (minute < 10) "0$minute" else "$minute"
            val time = "$hour:$min"
            if (op == 0) binding.inicioJornada.setText(time)
            else binding.finJornada.setText(time)
        })
        timePickerFragment.show(this.childFragmentManager, "timePicker")
    }

    private fun updateReportDialog(msg: String) {
        val dialog = requireContext().infoDialog(msg)
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            nav?.navigate(R.id.action_updateReportFragment_to_sendReportFragment)
            dialog.dismiss()
        }
    }

    private fun backDialog() {
        val dialog = requireContext().backToMain("Al salir del report perderá los cambios realizados, ¿desea continuar?")
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            nav?.navigate(R.id.action_updateReportFragment_to_sendReportFragment)
            dialog.dismiss()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().toolbar_title.text = ("Editar Reporte")
        requireActivity().btn_back.visibility = View.VISIBLE
        requireActivity().btn_back.setOnClickListener {
            backDialog()
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            backDialog()
        }
    }
}