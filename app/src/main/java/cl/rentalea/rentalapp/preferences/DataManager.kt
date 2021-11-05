package cl.rentalea.rentalapp.preferences

import android.content.Context
import android.content.SharedPreferences

class DataManager private constructor(){

    companion object {
        private const val PRIVATE_MODE: Int = 0
        private const val PREF_NAME: String = "myPref"

        const val IS_LOGIN: String = "isLoggedIn"
        const val USER_NAME: String = "userName"
        const val USER_DNI: String = "userDni"

        private lateinit var pref: SharedPreferences
        lateinit var edit: SharedPreferences.Editor
        private val INSTANCE = DataManager()

        fun getInstance(context: Context): DataManager {
            if (!::pref.isInitialized) {
                synchronized(this) {
                    if (!::pref.isInitialized) {
                        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
                        edit = pref.edit()
                    }
                }
            }
            return INSTANCE
        }
    }

    fun createUserSession(username: String, userDni: String) {
        edit.putBoolean(IS_LOGIN, true)
        edit.putString(USER_NAME, username)
        edit.putString(USER_DNI, userDni)
        edit.commit()
    }

    fun getUserData(): HashMap<String, String> {
        val userData = HashMap<String, String>()
        userData[USER_NAME] =  pref.getString(USER_NAME, null)!!
        userData[USER_DNI] =  pref.getString(USER_DNI, null)!!
        return userData
    }


    fun logout(){
        edit.clear()
        edit.commit()
    }


    fun isLoggedIn(): Boolean = pref.getBoolean(IS_LOGIN, false)
}