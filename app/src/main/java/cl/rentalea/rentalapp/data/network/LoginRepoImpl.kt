package cl.rentalea.rentalapp.data.network

import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.db.entity.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginRepoImpl: LoginRepo {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun getUserFromFirestore(rut: String, password: String): Respuesta<User> {
        val resultData = firestore.collection("operadores").get().await()
        var user: User? = null
        for (document in resultData) {
            if (document.getString("rut") == rut && document.getString("password") == password) {
                user = User(
                    0,
                    document.get("nombre").toString(),
                    document.get("rut").toString(),
                    document.get("cargo").toString()
                )
            }
        }

        return Respuesta.Success(user!!)
    }
}