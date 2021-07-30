package cl.rentalea.rentalapp.repository.reportRepository

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.db.entity.Viaje

interface ReportRepository {

    suspend fun insertReport(report: Report)

    suspend fun getReport(numReport: Int): Report

    suspend fun deleteReport(report: Report)

    suspend fun updateReport(report: Report)

    suspend fun getReports(): Respuesta<MutableList<Report>>

    suspend fun saveReportInFirestore(report: Report, viajes: MutableList<Viaje>): Respuesta<Boolean>

    suspend fun getEquiposList(tipoEquipo: String): MutableList<String>

    suspend fun getPatentesList(equipo: String): MutableList<String>

    suspend fun getObrasList(): MutableList<String>

    suspend fun getEmpresasList(): MutableList<String>

    suspend fun getMaterialesList(): MutableList<String>

    suspend fun getAditamentosList(): MutableList<String>

    suspend fun getAccesoriosList(): MutableList<String>

    suspend fun insertViajeData(viaje: Viaje)

    suspend fun getViajesList(reportNumber: Int): MutableList<Viaje>

    suspend fun deleteViaje(reportNumber: Int)

}