package cl.rentalea.rentalapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.LayoutRes
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.DataBindingActivity
import cl.rentalea.rentalapp.databinding.ActivityLoginBinding


class LoginActivity : DataBindingActivity<ActivityLoginBinding>() {
    @LayoutRes
    override fun getLayoutResId(): Int = R.layout.activity_login
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@LoginActivity
        }
    }

}