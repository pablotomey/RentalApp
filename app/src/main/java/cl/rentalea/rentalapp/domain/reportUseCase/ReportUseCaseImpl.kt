package cl.rentalea.rentalapp.domain.reportUseCase

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.repository.reportRepository.ReportRepository
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.db.entity.Viaje

class ReportUseCaseImpl(private val reportRepository: ReportRepository):
    ReportUseCase {

    override suspend fun insertReport(report: Report) {
        reportRepository.insertReport(report)
    }

    override suspend fun getReports(): Respuesta<MutableList<Report>> {
        return reportRepository.getReports()
    }

    // Send report to Firestore
    override suspend fun sendReport(report: Report, viajes: MutableList<Viaje>): Respuesta<Boolean> {
        return reportRepository.saveReportInFirestore(report, viajes)
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

    override suspend fun getObrasList(): MutableList<String> {
        return reportRepository.getObrasList()
    }

    override suspend fun getEmpresasList(): MutableList<String> {
        return reportRepository.getEmpresasList()
    }

    override suspend fun getMaterialesList(): MutableList<String> {
        return reportRepository.getMaterialesList()
    }

    override suspend fun getAditamentosList(): MutableList<String> {
        return reportRepository.getAditamentosList()
    }

    override suspend fun getAccesoriosList(): MutableList<String> {
        return reportRepository.getAccesoriosList()
    }

    override suspend fun insertViajeData(viaje: Viaje) {
        reportRepository.insertViajeData(viaje)
    }

    override suspend fun getViajesList(reportNumber: Int): MutableList<Viaje> {
        return reportRepository.getViajesList(reportNumber)
    }

    override suspend fun deleteViaje(reportNumber: Int) {
        reportRepository.deleteViaje(reportNumber)
    }
}