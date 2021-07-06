package cl.rentalea.rentalapp.repository.reportRepository

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.datasource.DataSource
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.db.entity.Viaje
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ReportRepositoryImpl(private val dataSource: DataSource): ReportRepository {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun insertReport(report: Report) {
        dataSource.insertReport(report)
    }

    override suspend fun getReports(): Respuesta<MutableList<Report>> {
        return dataSource.getReports()
    }

    override suspend fun deleteReport(report: Report) {
        dataSource.deleteReport(report)
    }

    override suspend fun getEquiposList(tipoEquipo: String): Respuesta<MutableList<String>> {
        return dataSource.getEquiposList(tipoEquipo)
    }

    override suspend fun getPatentesList(equipo: String): Respuesta<MutableList<String>> {
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
        val reportMap = hashMapOf(
            "operador" to report.operador,
            "fecha" to report.date,
            "numero_report" to report.report_number,
            "equipo" to report.equipo,
            "tipo_equipo" to report.tipo_equipo,
            "patente" to report.patente,
            "obra" to report.obra,
            "empresa" to report.empresa,
            "horometro_inicial" to report.horometro_inicial,
            "horometro_final" to report.horometro_final,
            "diferencia_horometro" to report.diferencia_horometro,
            "kilometraje_inicial" to report.kilometraje_inicial,
            "kilometraje_final" to report.kilometraje_final,
            "litros_combustible" to report.litros_combustible,
            "horometro_combustible" to report.horometro_combustible,
            "inicio_jornada" to report.inicio_jornada,
            "fin_jornada" to report.fin_jornada,
            "observaciones" to report.observaciones,
            "firma_supervisor" to report.firma_supervisor,
            "firma_operador" to report.firma_operador
        )

        return try {
            val reportRef = firestore.collection("reports").document(report.report_number.toString())
            val viajeRef = firestore.collection("reports").document(report.report_number.toString())
                .collection("viajes").document()
            firestore.runBatch { batch ->
                batch.set(reportRef, report)

                if (viajes.isNotEmpty()) {
                    for (viaje in viajes) {
                        batch.set(viajeRef, viaje)
                    }
                }


            }.await()
            Respuesta.Success(true)
        } catch (e: Exception) {
            Respuesta.Success(false)
        }
    }
}