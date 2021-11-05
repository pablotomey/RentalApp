package cl.rentalea.rentalapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.binding.DataBindingActivity
import cl.rentalea.rentalapp.databinding.ActivityMainBinding
import cl.rentalea.rentalapp.preferences.DataManager

class MainActivity : DataBindingActivity<ActivityMainBinding>() {

    private val dataManager by lazy { DataManager.getInstance(this) }

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MainActivity
        }

        if (!dataManager.isLoggedIn()) goToLoginActivity()
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}