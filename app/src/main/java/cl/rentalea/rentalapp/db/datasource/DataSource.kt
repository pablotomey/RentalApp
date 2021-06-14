package cl.rentalea.rentalapp.db.datasource

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.AppDataBase
import cl.rentalea.rentalapp.db.entity.*

class DataSource(private val roomDataBase: AppDataBase) {

    suspend fun insertUser(user: User) {
        roomDataBase.userDao().saveUser(user)
    }

    suspend fun insertVehiculos(vehiculo: Vehiculo) {
        roomDataBase.vehiculoDao().insertVehiculo(vehiculo)
    }

    suspend fun insertObra(obra: Obra) {
        roomDataBase.obraDao().insertObra(obra)
    }

    suspend fun insertEmpresa(empresa: Empresa) {
        roomDataBase.empresaDao().insertEmpresa(empresa)
    }

    suspend fun insertMaterial(material: Material) {
        roomDataBase.materialDao().insertMaterial(material)
    }

    suspend fun insertAditamento(aditamento: Aditamento) {
        roomDataBase.aditamentoDao().insertAditamento(aditamento)
    }

    suspend fun insertAccesorio(accesorio: Accesorio) {
        roomDataBase.accesorioDao().insertAccesorio(accesorio)
    }

    suspend fun cleanVehiculos() {
        roomDataBase.vehiculoDao().cleanVehiculos()
    }

    suspend fun insertEquipos(equipo: Equipo) {
        roomDataBase.equipoDao().insertEquipo(equipo)
    }

    suspend fun cleanEquipos() {
        roomDataBase.equipoDao().cleanEquipos()
    }

    suspend fun insertReport(report: Report) {
        roomDataBase.reportDao().addReport(report)
    }

    suspend fun getReports(): Respuesta<MutableList<Report>> {
        return Respuesta.Success(roomDataBase.reportDao().getAllReports())
    }

    suspend fun deleteReport(report: Report) {
        roomDataBase.reportDao().deleteReport(report)
    }

    suspend fun getEquiposList(tipoEquipo: String): Respuesta<MutableList<String>> {
        return Respuesta.Success(roomDataBase.equipoDao().getEquiposList(tipoEquipo))
    }

    suspend fun getPatentesList(equipo: String): Respuesta<MutableList<String>> {
        return Respuesta.Success(roomDataBase.vehiculoDao().getPatentesList(equipo))
    }
}