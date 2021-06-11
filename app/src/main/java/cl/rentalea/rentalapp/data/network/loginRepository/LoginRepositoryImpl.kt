package cl.rentalea.rentalapp.data.network.loginRepository

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.datasource.DataSource
import cl.rentalea.rentalapp.db.entity.Equipo
import cl.rentalea.rentalapp.db.entity.User
import cl.rentalea.rentalapp.db.entity.Vehiculo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl(private val dataSource: DataSource): LoginRepository {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun insertUser(user: User) {
        dataSource.insertUser(user)
    }

    override suspend fun insertVehiculos(vehiculo: Vehiculo) {
        dataSource.insertVehiculos(vehiculo)
    }

    override suspend fun cleanVehiculos() {
        dataSource.cleanVehiculos()
    }

    override suspend fun insertEquipos(equipo: Equipo) {
        dataSource.insertEquipos(equipo)
    }

    override suspend fun cleanEquipos() {
        dataSource.cleanEquipos()
    }

    override suspend fun getUserFromFirestore(rut: String, password: String): Respuesta<User> {
        val resultData = firestore.collection("operadores").get().await()
        var user: User? = null
        for (document in resultData) {
            if (document.getString("rut") == rut && document.getString("password") == password) {
                user = User(
                    1,
                    document.get("nombre").toString(),
                    document.get("rut").toString(),
                    document.get("cargo").toString()
                )
            }
        }

        return Respuesta.Success(user!!)
    }

    override suspend fun getVehiculosFromFirestore(): Respuesta<MutableList<Vehiculo>> {
        val vehiculosList: MutableList<Vehiculo> = mutableListOf()
        val resultData = firestore.collection("vehiculos").get().await()

        for (vehiculo in resultData) {
            vehiculosList.add(
                Vehiculo(
                    0,
                    vehiculo.getString("equipo")!!,
                    vehiculo.getString("tipo_equipo")!!,
                    vehiculo.getString("patente")!!

                )
            )
        }
        return Respuesta.Success(vehiculosList)
    }

    override suspend fun getEquiposFromFirestore(): Respuesta<MutableList<Equipo>> {
        val equiposList: MutableList<Equipo> = mutableListOf()
        val resultData = firestore.collection("equipos").get().await()

        for (equipo in resultData) {
            equiposList.add(
                Equipo(
                    0,
                    equipo.getString("equipo")!!,
                    equipo.getString("tipo_equipo")!!
                )
            )
        }
        return Respuesta.Success(equiposList)
    }
}