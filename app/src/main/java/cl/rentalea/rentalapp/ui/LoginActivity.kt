package cl.rentalea.rentalapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.binding.DataBindingActivity
import cl.rentalea.rentalapp.databinding.ActivityLoginBinding
import cl.rentalea.rentalapp.preferences.DataManager


class LoginActivity : DataBindingActivity<ActivityLoginBinding>() {

    val dataManager by lazy { DataManager.getInstance(this) }

    @LayoutRes
    override fun getLayoutResId(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@LoginActivity
        }

        if (dataManager.isLoggedIn()) goToMainActivity()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}