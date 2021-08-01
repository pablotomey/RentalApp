package cl.rentalea.rentalapp.repository.loginRepository

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.datasource.DataSource
import cl.rentalea.rentalapp.db.entity.*
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

    override suspend fun insertObra(obra: Obra) {
        dataSource.insertObra(obra)
    }

    override suspend fun insertEmpresa(empresa: Empresa) {
        dataSource.insertEmpresa(empresa)
    }

    override suspend fun insertMaterial(material: Material) {
        dataSource.insertMaterial(material)
    }

    override suspend fun insertAccesorio(accesorio: Accesorio) {
        dataSource.insertAccesorio(accesorio)
    }

    override suspend fun insertAditamento(aditamento: Aditamento) {
        dataSource.insertAditamento(aditamento)
    }

    override suspend fun insertCheckListItem(checkListItem: CheckListItem) {
        dataSource.insertCheckListItem(checkListItem)
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
                    vehiculo.id,
                    vehiculo.getString("equipo")!!
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
                    equipo.id.toInt(),
                    equipo.getString("equipo")!!,
                    equipo.getString("tipo_equipo")!!
                )
            )
        }
        return Respuesta.Success(equiposList)
    }

    override suspend fun getObrasFromFirestore(): Respuesta<MutableList<Obra>> {
        val obraList: MutableList<Obra> = mutableListOf()
        val resultData = firestore.collection("obras").get().await()

        for (obra in resultData) {
            obraList.add(
                Obra(
                    obra.id.toInt(),
                    obra.getString("obra")!!
                )
            )
        }
        return Respuesta.Success(obraList)
    }

    override suspend fun getEmpresasFromFirestore(): Respuesta<MutableList<Empresa>> {
        val empresaList: MutableList<Empresa> = mutableListOf()
        val resultData = firestore.collection("empresas").get().await()

        for (empresa in resultData) {
            empresaList.add(
                Empresa(
                    empresa.id.toInt(),
                    empresa.getString("empresa")!!
                )
            )
        }
        return Respuesta.Success(empresaList)
    }

    override suspend fun getMaterialesFromFirestore(): Respuesta<MutableList<Material>> {
        val materialList: MutableList<Material> = mutableListOf()
        val resultData = firestore.collection("materiales").get().await()

        for (material in resultData) {
            materialList.add(
                Material(
                    material.id.toInt(),
                    material.getString("tipo_material")!!
                )
            )
        }
        return Respuesta.Success(materialList)
    }

    override suspend fun getAccesoriosFromFirestore(): Respuesta<MutableList<Accesorio>> {
        val accesorioList: MutableList<Accesorio> = mutableListOf()
        val resultData = firestore.collection("accesorios").get().await()

        for (accesorio in resultData) {
            accesorioList.add(
                Accesorio(
                    accesorio.id.toInt(),
                    accesorio.getString("accesorio")!!
                )
            )
        }
        return Respuesta.Success(accesorioList)
    }

    override suspend fun getAditamentosFromFirestore(): Respuesta<MutableList<Aditamento>> {
        val aditamentolList: MutableList<Aditamento> = mutableListOf()
        val resultData = firestore.collection("aditamentos").get().await()

        for (aditamento in resultData) {
            aditamentolList.add(
                Aditamento(
                    aditamento.id.toInt(),
                    aditamento.getString("aditamento")!!
                )
            )
        }
        return Respuesta.Success(aditamentolList)
    }

    override suspend fun getCheckListItemsFromFirestore(): Respuesta<MutableList<CheckListItem>> {
        val itemList: MutableList<CheckListItem> = mutableListOf()
        val resultData = firestore.collection("check_list_items").get().await()

        for (item in resultData) {
            itemList.add(
                CheckListItem(
                    item.id.toInt(),
                    item.getString("item_name")!!,
                    item.get("status")!!.toString().toInt()
                )
            )
        }
        return Respuesta.Success(itemList)
    }
}