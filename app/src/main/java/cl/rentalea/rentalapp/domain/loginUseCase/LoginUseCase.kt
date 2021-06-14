package cl.rentalea.rentalapp.domain.loginUseCase

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.*

interface LoginUseCase {

    // Consultar usuario de firebase
    suspend fun getUser(rut: String, password: String): Respuesta<User>

    // Insertar usuario
    suspend fun insertUser(user: User)

    // Obtener lista de equipos desde Firestore
    suspend fun getVehiculosFromFirestore(): Respuesta<MutableList<Vehiculo>>

    suspend fun getEquiposFromFirestore(): Respuesta<MutableList<Equipo>>

    suspend fun getObrasFromFirestore(): Respuesta<MutableList<Obra>>

    suspend fun getEmpresasFromFirestore(): Respuesta<MutableList<Empresa>>

    suspend fun getMaterialesFromFirestore(): Respuesta<MutableList<Material>>

    suspend fun getAccesoriosFromFirestore(): Respuesta<MutableList<Accesorio>>

    suspend fun getAditamentosFromFirestore(): Respuesta<MutableList<Aditamento>>

    suspend fun insertVehiculos(vehiculo: Vehiculo)

    suspend fun cleanVehiculos()

    suspend fun insertEquipos(equipo: Equipo)

    suspend fun insertObra(obra: Obra)

    suspend fun insertEmpresa(empresa: Empresa)

    suspend fun insertMaterial(material: Material)

    suspend fun insertAccesorio(accesorio: Accesorio)

    suspend fun insertAditamento(aditamento: Aditamento)

    suspend fun cleanEquipos()
}