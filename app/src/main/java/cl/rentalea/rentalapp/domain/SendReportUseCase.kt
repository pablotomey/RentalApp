package cl.rentalea.rentalapp.domain

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.Report

interface SendReportUseCase {

    suspend fun sendReport(report: Report): Respuesta<Boolean>

    suspend fun deleteReport(report: Report)
}