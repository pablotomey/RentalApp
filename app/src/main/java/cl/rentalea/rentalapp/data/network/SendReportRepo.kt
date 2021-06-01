package cl.rentalea.rentalapp.data.network

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.Report

interface SendReportRepo {

    suspend fun saveReportInFirestore(report: Report): Respuesta<Boolean>
}