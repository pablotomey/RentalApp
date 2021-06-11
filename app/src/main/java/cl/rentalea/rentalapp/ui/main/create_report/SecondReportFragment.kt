package cl.rentalea.rentalapp.ui.main.create_report

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentSecondReportBinding
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.utils.Constants
import cl.rentalea.rentalapp.utils.Constants.OPERATOR
import cl.rentalea.rentalapp.utils.TimePickerFragment
import cl.rentalea.rentalapp.utils.alert
import cl.rentalea.rentalapp.utils.backToMain
import kotlinx.android.synthetic.main.toolbar_main.*
import org.koin.android.viewmodel.ext.android.getViewModel

class SecondReportFragment : DataBindingFragment<FragmentSecondReportBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_second_report

    private lateinit var operador: String
    private lateinit var date: String
    private lateinit var numeroReport: String
    private lateinit var equipo: String
    private lateinit var tipoEquipo: String
    private lateinit var patente: String
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
            tipoEquipo = it.getString(FirstReportFragment.TIPO_EQUIPO)!!
            patente = it.getString(FirstReportFragment.PATENTE)!!
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

        binding.op = OPERATOR

        val viajesAridos = binding.viajesDataReport.viajesAridos
        val metrosCubicosViaje = binding.viajesDataReport.metrosCubicosViaje
        val metrosCubicosTotales = binding.viajesDataReport.metrosCubicosTotales
        val cantidadCombustible = binding.combustibleDataReport.cantidadCombustible
        val horometroCombustible = binding.combustibleDataReport.horometroCombustible
        val inicioJornada = binding.jornadaDataReport.inicioJornada
        val finJornada = binding.jornadaDataReport.finJornada
        val observaciones = binding.jornadaDataReport.observaciones
        val firmaOperador = binding.firmaDataReport.firmaChofer
        val firmaSupervisorSi = binding.firmaDataReport.firmaSi

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
                            0,
                            operador,
                            date,
                            numeroReport,
                            equipo,
                            tipoEquipo,
                            patente,
                            obra,
                            empresa,
                            horometroInicial,
                            horometroFinal,
                            diferenciaHorometro,
                            kilometrajeInicial,
                            kilometrajeFinal,
                            viajesAridos.text.toString(),
                            metrosCubicosViaje.text.toString(),
                            metrosCubicosTotales.text.toString(),
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
        val timePickerFragment = TimePickerFragment.newInstance(TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val hour = if (hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"
            val min = if (minute < 10) "0$minute" else "$minute"
            val time = "$hour:$min"
            if (op == 0) binding.jornadaDataReport.inicioJornada.setText(time)
            else binding.jornadaDataReport.finJornada.setText(time)
        })
        timePickerFragment.show(this.childFragmentManager, "timePicker")
    }

    private fun showSaveReportDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Report Guardado")
        alertDialog.setIcon(R.drawable.ic_check)
        alertDialog.setMessage("Se ha guardado exitosamente el report.")
        alertDialog.setPositiveButton("Aceptar") { dialog, which ->
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