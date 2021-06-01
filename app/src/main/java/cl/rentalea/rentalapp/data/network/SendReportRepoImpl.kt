package cl.rentalea.rentalapp.data.network

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.Report
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SendReportRepoImpl: SendReportRepo {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun saveReportInFirestore(report: Report): Respuesta<Boolean> {
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
            "viajes_aridos" to report.viajes_aridos,
            "metros_cubicos_viaje" to report.metros_cubicos_viaje,
            "metros_cubicos_totales" to report.metros_cubicos_totales,
            "litros_combustible" to report.litros_combustible,
            "horometro_combustible" to report.horometro_combustible,
            "inicio_jornada" to report.inicioJornada,
            "fin_jornada" to report.finJornada,
            "observaciones" to report.observaciones,
            "firma_supervisor" to report.firma_supervisor,
            "firma_operador" to report.firma_operador
        )

        return try {
            firestore.collection("reports").document().set(reportMap).await()
            Respuesta.Success(true)
        } catch (e: Exception) {
            Respuesta.Success(false)
        }
    }
}