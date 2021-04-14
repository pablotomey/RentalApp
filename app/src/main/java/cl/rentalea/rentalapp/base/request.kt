package cl.rentalea.rentalapp.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T : Any> request(call: suspend () -> Response<T>): Respuesta<T> {
    return try {
        val myResp = call.invoke()
        if (myResp.isSuccessful) {
            Respuesta.Success(myResp.body()!!)
        } else {
            Timber.e("REQUEST RESPONSE: ${myResp.code()}")
            val errorMsgJson = withContext(Dispatchers.IO) { JSONObject(myResp.errorBody()!!.string())}
            when (myResp.code()) {
                401 -> Respuesta.Failure(errorMsgJson.getString("message"))
                408 -> Respuesta.Failure("Problemas con la conexión a internet. Intente nuevamente")
                500 -> Respuesta.Failure("Servicio caído, reintente mas tarde")
                else -> {
                    Timber.e("CODE: ${myResp.code()}")
                    Respuesta.Failure(myResp.errorBody()?.string() ?: "Algo salió mal")
                }
            }
        }
    } catch (e: Exception) {
        Timber.e("EXCEPTION: $e")
        when (e) {
            is HttpException -> Respuesta.Failure("Problemas con la conexión internet, reintente nuevamente")
            is IOException -> Respuesta.Failure("Problemas con la conexión internet, reintente nuevamente")
            is SocketTimeoutException -> Respuesta.Failure("Problemas con la conexión internet, reintente nuevamente")
            else -> Respuesta.Failure(e.message ?: "Internet error runs")
        }
    }
}