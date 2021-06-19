package cl.rentalea.rentalapp.repository.loginRepository

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.*

interface LoginRepository {

    suspend fun insertUser(user: User)

    suspend fun insertVehiculos(vehiculo: Vehiculo)

    suspend fun insertEquipos(equipo: Equipo)

    suspend fun insertObra(obra: Obra)

    suspend fun insertEmpresa(empresa: Empresa)

    suspend fun insertMaterial(material: Material)

    suspend fun insertAccesorio(accesorio: Accesorio)

    suspend fun insertAditamento(aditamento: Aditamento)

    suspend fun cleanVehiculos()

    suspend fun cleanEquipos()

    suspend fun getUserFromFirestore(rut: String, password: String): Respuesta<User>

    suspend fun getVehiculosFromFirestore(): Respuesta<MutableList<Vehiculo>>

    suspend fun getEquiposFromFirestore(): Respuesta<MutableList<Equipo>>

    suspend fun getObrasFromFirestore(): Respuesta<MutableList<Obra>>

    suspend fun getEmpresasFromFirestore(): Respuesta<MutableList<Empresa>>

    suspend fun getMaterialesFromFirestore(): Respuesta<MutableList<Material>>

    suspend fun getAccesoriosFromFirestore(): Respuesta<MutableList<Accesorio>>

    suspend fun getAditamentosFromFirestore(): Respuesta<MutableList<Aditamento>>



}