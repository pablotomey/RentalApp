package cl.rentalea.rentalapp.base

sealed class Respuesta<out T: Any> {
    data class Success<out T: Any>(val data: T): Respuesta<T>()
    data class Failure(val exception: String): Respuesta<Nothing>()
}