package cl.rentalea.rentalapp.domain.reportUseCase

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.Report

interface ReportUseCase {

    suspend fun insertReport(report: Report)

    suspend fun getReports(): Respuesta<MutableList<Report>>

    suspend fun sendReport(report: Report): Respuesta<Boolean>

    suspend fun deleteReport(report: Report)

    suspend fun getEquiposList(tipoEquipo: String): Respuesta<MutableList<String>>

    suspend fun getPatentesList(equipo: String): Respuesta<MutableList<String>>

}