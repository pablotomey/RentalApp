package cl.rentalea.rentalapp.domain.loginUseCase

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.repository.loginRepository.LoginRepository
import cl.rentalea.rentalapp.db.entity.*

class LoginUseCaseImpl(private val loginRepository: LoginRepository): LoginUseCase {

    override suspend fun getUser(rut: String, password: String): Respuesta<User> {
        return loginRepository.getUserFromFirestore(rut, password)
    }

    override suspend fun insertUser(user: User) {
        loginRepository.insertUser(user)
    }

    override suspend fun getVehiculosFromFirestore(): Respuesta<MutableList<Vehiculo>> {
        return loginRepository.getVehiculosFromFirestore()
    }

    override suspend fun getEquiposFromFirestore(): Respuesta<MutableList<Equipo>> {
        return loginRepository.getEquiposFromFirestore()
    }

    override suspend fun getObrasFromFirestore(): Respuesta<MutableList<Obra>> {
        return loginRepository.getObrasFromFirestore()
    }

    override suspend fun getEmpresasFromFirestore(): Respuesta<MutableList<Empresa>> {
        return loginRepository.getEmpresasFromFirestore()
    }

    override suspend fun getMaterialesFromFirestore(): Respuesta<MutableList<Material>> {
        return loginRepository.getMaterialesFromFirestore()
    }

    override suspend fun getAccesoriosFromFirestore(): Respuesta<MutableList<Accesorio>> {
        return loginRepository.getAccesoriosFromFirestore()
    }

    override suspend fun getAditamentosFromFirestore(): Respuesta<MutableList<Aditamento>> {
        return loginRepository.getAditamentosFromFirestore()
    }

    override suspend fun getCheckListItemsFromFirestore(): Respuesta<MutableList<CheckListItem>> {
        return loginRepository.getCheckListItemsFromFirestore()
    }

    override suspend fun insertVehiculos(vehiculo: Vehiculo) {
        loginRepository.insertVehiculos(vehiculo)
    }

    override suspend fun cleanVehiculos() {
        loginRepository.cleanVehiculos()
    }

    override suspend fun insertEquipos(equipo: Equipo) {
        loginRepository.insertEquipos(equipo)
    }

    override suspend fun insertObra(obra: Obra) {
        loginRepository.insertObra(obra)
    }

    override suspend fun insertEmpresa(empresa: Empresa) {
        loginRepository.insertEmpresa(empresa)
    }

    override suspend fun insertMaterial(material: Material) {
        loginRepository.insertMaterial(material)
    }

    override suspend fun insertAccesorio(accesorio: Accesorio) {
        loginRepository.insertAccesorio(accesorio)
    }

    override suspend fun insertAditamento(aditamento: Aditamento) {
        loginRepository.insertAditamento(aditamento)
    }

    override suspend fun insertCheckListItem(checkListItem: CheckListItem) {
        loginRepository.insertCheckListItem(checkListItem)
    }

    override suspend fun cleanEquipos() {
        loginRepository.cleanEquipos()
    }
}