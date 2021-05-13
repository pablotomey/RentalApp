package cl.rentalea.rentalapp.ui.main.create_report

import android.os.Bundle
import android.view.View
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentSecondReportBinding
import cl.rentalea.rentalapp.model.entity.Report
import cl.rentalea.rentalapp.utils.alert
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
            vm = getViewModel()
            lifecycleOwner = this@SecondReportFragment
        }

        val viajesAridos = binding.viajesDataReport.viajesAridos
        val metrosCubicosViaje = binding.viajesDataReport.metrosCubicosViaje
        val metrosCubicosTotales = binding.viajesDataReport.metrosCubicosTotales
        val cantidadCombustible = binding.combustibleDataReport.cantidadCombustible
        val horometroCombustible = binding.combustibleDataReport.horometroCombustible
        val jornada = binding.jornadaDataReport.jornada
        val observaciones = binding.jornadaDataReport.observaciones
        val firmaOperador = binding.firmaDataReport.firmaChofer
        val firmaSupervisorSi = binding.firmaDataReport.firmaSi

        binding.btnGuardar.setOnClickListener {
            when {
                cantidadCombustible.text.isNullOrEmpty() -> requireContext().alert(0, "Debe ingresar la cantidad de combustible").show()
                horometroCombustible.text.isNullOrEmpty() -> requireContext().alert(0, "Debe ingresar el horómetro de carga de comustible").show()
                else -> {
                    val firmaSupervisor = if (firmaSupervisorSi.isChecked) "Si" else "No"

                    binding.vm?.addReport(
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
                            jornada.text.toString(),
                            observaciones.text.toString(),
                            firmaSupervisor,
                            firmaOperador.text.toString()
                        )
                    )
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().toolbar_title.text = ("Ingreso de report 2/2")
        requireActivity().btn_back.visibility = View.VISIBLE
        requireActivity().btn_back.setOnClickListener {
            nav!!.navigate(R.id.action_secondReportFragment_to_mainFragment)
        }
    }


}