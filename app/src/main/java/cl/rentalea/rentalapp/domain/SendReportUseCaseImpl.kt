package cl.rentalea.rentalapp.domain

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.data.network.SendReportRepo
import cl.rentalea.rentalapp.db.datasource.DataSource
import cl.rentalea.rentalapp.db.entity.Report

class SendReportUseCaseImpl(private val dataSource: DataSource, private val sendReportRepo: SendReportRepo): SendReportUseCase {

    // Send report to Firestore
    override suspend fun sendReport(report: Report): Respuesta<Boolean> {
        return sendReportRepo.saveReportInFirestore(report)
    }
    // Delete report in Room
    override suspend fun deleteReport(report: Report) {
        dataSource.deleteReport(report)
    }


}