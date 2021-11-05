package cl.rentalea.rentalapp.ui.main.create_report

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentSecondReportBinding
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.db.entity.Viaje
import cl.rentalea.rentalapp.preferences.DataManager
import cl.rentalea.rentalapp.ui.adapter.ViajesAdapter
import cl.rentalea.rentalapp.utils.*
import cl.rentalea.rentalapp.utils.Constants.MATERIALES
import cl.rentalea.rentalapp.utils.Constants.VIAJES
import kotlinx.android.synthetic.main.toolbar_main.*
import kotlinx.android.synthetic.main.viajes_dialog_layout.*
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber

class SecondReportFragment : DataBindingFragment<FragmentSecondReportBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_second_report

    private val viajesList: MutableList<Viaje> = mutableListOf()

    private lateinit var operador: String
    private lateinit var date: String
    private lateinit var numeroReport: String
    private lateinit var equipo: String
    private lateinit var equipoArrastre: String
    private lateinit var tipoEquipo: String
    private lateinit var patente: String
    private lateinit var aditamento: String
    private lateinit var obra: String
    private lateinit var empresa: String
    private lateinit var horometroInicial: String
    private lateinit var horometroFinal: String
    private lateinit var diferenciaHorometro: String
    private lateinit var kilometrajeInicial: String
    private lateinit var kilometrajeFinal: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            operador = it?.getString(FirstReportFragment.OPERADOR)!!
            date = it.getString(FirstReportFragment.FECHA_REPORTE)!!
            numeroReport = it.getString(FirstReportFragment.NUMERO_REPORTE)!!
            equipo = it.getString(FirstReportFragment.EQUIPO)!!
            equipoArrastre = it.getString(FirstReportFragment.EQUIPO_ARRASTRE)!!
            tipoEquipo = it.getString(FirstReportFragment.TIPO_EQUIPO)!!
            patente = it.getString(FirstReportFragment.PATENTE)!!
            aditamento = it.getString(FirstReportFragment.ADITAMENTO)!!
            obra = it.getString(FirstReportFragment.NOMBRE_OBRA)!!
            empresa = it.getString(FirstReportFragment.NOMBRE_EMPRESA)!!
            horometroInicial = it.getString(FirstReportFragment.HOROMETRO_INICIAL)!!
            horometroFinal = it.getString(FirstReportFragment.HOROMETRO_FINAL)!!
            diferenciaHorometro = it.getString(FirstReportFragment.DIFERENCIA_HOROMETRO)!!
            kilometrajeInicial = it.getString(FirstReportFragment.KILOMETRAJE_INICIAL)!!
            kilometrajeFinal = it.getString(FirstReportFragment.KILOMETRAJE_FINAL)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            reportViewModel = getViewModel()
            lifecycleOwner = this@SecondReportFragment
        }

        binding.dataManager = DataManager.getInstance(requireContext())

        getTipoMaterialesListObserve()

        val cantidadCombustible = binding.combustibleDataReport.cantidadCombustible
        val horometroCombustible = binding.combustibleDataReport.horometroCombustible
        val inicioJornada = binding.jornadaDataReport.inicioJornada
        val finJornada = binding.jornadaDataReport.finJornada
        val observaciones = binding.jornadaDataReport.observaciones
        val firmaOperador = binding.firmaDataReport.firmaChofer
        val firmaSupervisorSi = binding.firmaDataReport.firmaSi

        binding.viajesDataReport.agregarViajeBtn.setOnClickListener {
            if (MATERIALES!!.isNotEmpty()) {
                val dialog = requireContext().addViaje(MATERIALES!!).show()
                dialog.mt_cubicos.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val mt3Totales: Int
                        var viajes = dialog.cantidad_viajes.text.toString()
                        var mt3PorViaje = dialog.mt_cubicos.text.toString()

                        if (dialog.cantidad_viajes.text.isNullOrEmpty()) viajes = "0"
                        if (dialog.mt_cubicos.text.isNullOrEmpty()) mt3PorViaje = "0"

                        if (dialog.cantidad_viajes.text!!.isDigitsOnly() && dialog.mt_cubicos.text!!.isDigitsOnly()) {
                            mt3Totales = viajes.toInt() * mt3PorViaje.toInt()
                            dialog.mt_cubicos_totales.setText(mt3Totales.toString())
                        }
                    }

                })
                dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
                    val error =
                        when {
                            dialog.tipo_material_option.text.isNullOrEmpty() -> "Debe seleccionar un tipo de material."
                            dialog.cantidad_viajes.text.isNullOrEmpty() -> "Debe ingresar la cantidad de viajes."
                            dialog.mt_cubicos.text.isNullOrEmpty() -> "Debe ingresar los metros cúbicos de material por viaje."
                            else -> null
                        }

                    if (!error.isNullOrEmpty()) {
                        dialog.error_msg.text = error
                        dialog.error_msg.visibility = View.VISIBLE
                    } else {
                        val viaje = Viaje(
                            0,
                            dialog.tipo_material_option.text.toString(),
                            dialog.cantidad_viajes.text.toString().toInt(),
                            dialog.mt_cubicos.text.toString().toInt(),
                            dialog.mt_cubicos_totales.text.toString().toInt(),
                            numeroReport.toInt()
                        )

                        viajesList.add(viaje)
                        binding.reportViewModel?.addViaje(viaje)
                        Timber.e("VIAJES LIST $viajesList")
                        binding.viajesDataReport.viajesAdapter = ViajesAdapter(requireContext(), viajesList)
                        binding.viajesDataReport.viajesAdapter!!.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                }
            }
        }

        binding.jornadaDataReport.inicioJornada.setOnClickListener {
            showTimePickerDialog(0)
        }

        binding.jornadaDataReport.finJornada.setOnClickListener {
            showTimePickerDialog(1)
        }

        binding.btnGuardar.setOnClickListener {
            when {
                cantidadCombustible.text.isNullOrEmpty() -> requireContext().alert(0, "Debe ingresar la cantidad de combustible").show()
                horometroCombustible.text.isNullOrEmpty() -> requireContext().alert(0, "Debe ingresar el horómetro de carga de comustible").show()
                else -> {
                    val firmaSupervisor = if (firmaSupervisorSi.isChecked) "Si" else "No"

                    binding.reportViewModel?.addReport(
                        Report(
                            numeroReport.toInt(),
                            operador,
                            date,
                            equipo,
                            equipoArrastre,
                            tipoEquipo,
                            patente,
                            aditamento,
                            obra,
                            empresa,
                            horometroInicial,
                            horometroFinal,
                            diferenciaHorometro,
                            kilometrajeInicial,
                            kilometrajeFinal,
                            cantidadCombustible.text.toString(),
                            horometroCombustible.text.toString(),
                            inicioJornada.text.toString(),
                            finJornada.text.toString(),
                            observaciones.text.toString(),
                            firmaSupervisor,
                            firmaOperador.text.toString()
                        )
                    )

                    showSaveReportDialog()
                }
            }
        }
    }

    private fun showTimePickerDialog(op: Int) {
        val timePickerFragment = TimePickerFragment.newInstance(TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val hour = if (hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"
            val min = if (minute < 10) "0$minute" else "$minute"
            val time = "$hour:$min"
            if (op == 0) binding.jornadaDataReport.inicioJornada.setText(time)
            else binding.jornadaDataReport.finJornada.setText(time)
        })
        timePickerFragment.show(this.childFragmentManager, "timePicker")
    }

    private fun getTipoMaterialesListObserve() {
        binding.reportViewModel?.obtenerMateriales()?.observe(viewLifecycleOwner, Observer { materialesList ->
            MATERIALES = materialesList
        })
    }

    private fun getViajesListObserve(reportNumber: Int) {
        binding.reportViewModel?.obtenerViajes(reportNumber)?.observe(viewLifecycleOwner, Observer { viajesList ->
            Timber.e("$viajesList")
            binding.viajesDataReport.viajesAdapter = ViajesAdapter(requireContext(), viajesList)
            binding.viajesDataReport.viajesAdapter!!.notifyDataSetChanged()
        })
    }

    private fun showSaveReportDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Report Guardado")
        alertDialog.setIcon(R.drawable.ic_check)
        alertDialog.setMessage("Se ha guardado exitosamente el report.")
        alertDialog.setPositiveButton("Aceptar") { dialog, _ ->
            nav?.navigate(R.id.action_secondReportFragment_to_mainFragment)
            arguments?.clear()
            dialog.dismiss()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun backDialog() {
        val dialog = requireContext().backToMain("Al salir del report perderá los cambios guardados, ¿desea continuar?")
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            nav?.navigate(R.id.action_secondReportFragment_to_mainFragment)
            arguments?.clear()
            binding.reportViewModel?.deleteViaje(numeroReport.toInt())
            dialog.dismiss()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().toolbar_title.text = ("Ingreso de report 2/2")
        requireActivity().btn_back.visibility = View.VISIBLE
        requireActivity().btn_back.setOnClickListener {
            backDialog()
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            backDialog()
        }
    }
}