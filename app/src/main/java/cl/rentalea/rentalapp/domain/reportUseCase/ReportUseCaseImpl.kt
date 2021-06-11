package cl.rentalea.rentalapp.domain.reportUseCase

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.data.network.reportRepository.ReportRepository
import cl.rentalea.rentalapp.db.entity.Report

class ReportUseCaseImpl(private val reportRepository: ReportRepository):
    ReportUseCase {

    override suspend fun insertReport(report: Report) {
        reportRepository.insertReport(report)
    }

    override suspend fun getReports(): Respuesta<MutableList<Report>> {
        return reportRepository.getReports()
    }

    // Send report to Firestore
    override suspend fun sendReport(report: Report): Respuesta<Boolean> {
        return reportRepository.saveReportInFirestore(report)
    }
    // Delete report in Room
    override suspend fun deleteReport(report: Report) {
        reportRepository.deleteReport(report)
    }

    override suspend fun getEquiposList(tipoEquipo: String): Respuesta<MutableList<String>> {
        return reportRepository.getEquiposList(tipoEquipo)
    }

    override suspend fun getPatentesList(equipo: String): Respuesta<MutableList<String>> {
        return reportRepository.getPatentesList(equipo)
    }
}