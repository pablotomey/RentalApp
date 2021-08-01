package cl.rentalea.rentalapp.repository.reportRepository

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.datasource.DataSource
import cl.rentalea.rentalapp.db.entity.CheckListItem
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.db.entity.Viaje
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ReportRepositoryImpl(private val dataSource: DataSource): ReportRepository {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun insertReport(report: Report) {
        dataSource.insertReport(report)
    }

    override suspend fun getReport(numReport: Int): Report {
        return dataSource.getReport(numReport)
    }

    override suspend fun getReports(): Respuesta<MutableList<Report>> {
        return dataSource.getReports()
    }

    override suspend fun deleteReport(report: Report) {
        dataSource.deleteReport(report)
    }

    override suspend fun updateReport(report: Report) {
        dataSource.updateReport(report)
    }

    override suspend fun getEquiposList(tipoEquipo: String): MutableList<String> {
        return dataSource.getEquiposList(tipoEquipo)
    }

    override suspend fun getPatentesList(equipo: String): MutableList<String> {
        return dataSource.getPatentesList(equipo)
    }

    override suspend fun getObrasList(): MutableList<String> {
        return dataSource.getObrasList()
    }

    override suspend fun getEmpresasList(): MutableList<String> {
        return dataSource.getEmpresasList()
    }

    override suspend fun getMaterialesList(): MutableList<String> {
        return dataSource.getMaterialesList()
    }

    override suspend fun getAditamentosList(): MutableList<String> {
        return dataSource.getAditamentosList()
    }

    override suspend fun getAccesoriosList(): MutableList<String> {
        return dataSource.getAccesoriosList()
    }

    override suspend fun getCheckItemsList(): MutableList<CheckListItem> {
        return dataSource.getCheckItemsList()
    }

    override suspend fun insertViajeData(viaje: Viaje) {
        dataSource.insertViajeData(viaje)
    }

    override suspend fun getViajesList(reportNumber: Int): MutableList<Viaje> {
        return dataSource.getViajesList(reportNumber)
    }

    override suspend fun deleteViaje(reportNumber: Int) {
        dataSource.deleteViaje(reportNumber)
    }

    override suspend fun saveReportInFirestore(report: Report, viajes: MutableList<Viaje>): Respuesta<Boolean> {
        val viajesMap = hashMapOf( "viajes" to viajes)

        return try {
            val reportRef = firestore.collection("reports").document(report.report_number.toString())
            val viajeRef = firestore.collection("reports").document(report.report_number.toString())
                .collection("viajes").document()
            firestore.runBatch { batch ->
                batch.set(reportRef, report)
                batch.set(viajeRef, viajesMap)


            }.await()
            Respuesta.Success(true)
        } catch (e: Exception) {
            Respuesta.Success(false)
        }
    }
}