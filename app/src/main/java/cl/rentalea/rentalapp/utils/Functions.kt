package cl.rentalea.rentalapp.utils

fun validarRut(rut: String): Boolean {

    // Expresión regular para validar rut con guión y DV
    val pat = Regex("^[0-9]+-[0-9k]$")
    // Validamos el rut completo como cadena de texto contra la expReg
    val match = pat.matchEntire(rut)
    // Si match no coincide con la expresión regular, devuelve Null
    if (match === null) {
        return false
    }
    // Guardamos el dígito verificador cuando el rut sea de 9 o 10 dígitos
    val dv: CharSequence = when (rut.length) {
        9 -> rut.subSequence(8,9) // Separamos el DV y lo asignamos a la variable

        10 -> rut.subSequence(9,10) // Separamos el DV y lo asignamos a la variable

        else -> return false
    }

    // Variable guion
    val guion = '-'
    // Separamos el rut desde el guion -> rutAll es un array donde en la posición 0 queda la mantisa del rut
    val rutAll = rut.split(guion)
    // Mantisa del rut
    val numRut = rutAll[0]
    // Validamos el dígito verificador ingresado contra la función validadora del DV. Si es true retornamos el rut validado
    if (dv != validarDv(numRut.toInt())){
        return false
    }

    return true
}

fun validarDv (rut: Int): String {
    /* Esta función debe recibir la mantisa del rut (sin guion ni dígito verificador)
    *  Asi calculamos el dígito verificador corresponiente y devolvemos la validación
    * */
    var suma = 0 // suma para el algoritmo modulo 11
    var multiplicador = 1 // Multiplicador para el algoritmo modulo 11
    // Separamos cada caracter del rut y lo guardamos en un array
    val num = rut.toString().toCharArray()
    // Guardamos el rango del rut ingresado
    val rutRango = rut.toString().length -1
    // Recorremos cada dígito del rut
    for (i in rutRango downTo 0 ){ // equivalente a -> (int i = rut.ToString().Length - 1; i >= 0; i--)
        multiplicador ++
        // Según Modulo 11: cada dígito del rut se multiplica con los números del 2 al 7 respectivamente
        if (multiplicador == 8){
            multiplicador = 2
        }
        // guardamos en suma el resultante de la multiplicación
        suma += num[i].toString().toInt() * multiplicador
    }
    // Sacamos el restante de la division (Modulo %) de suma con el n° 11, lo restamos con 11 y nos da el digito verificador el rut
    // Si DV es igual a 10 u 11 se remplaza con 0 o k, si no se devuelve en número resultante
    return when (val dv = 11 - (suma.rem(11))) {
        11 -> "0"
        10 -> "k"
        else -> dv.toString()
    }
}