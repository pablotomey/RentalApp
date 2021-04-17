package cl.rentalea.rentalapp.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.DataBindingActivity
import cl.rentalea.rentalapp.databinding.ActivityMainBinding

class MainActivity : DataBindingActivity<ActivityMainBinding>() {

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MainActivity
        }
    }


}