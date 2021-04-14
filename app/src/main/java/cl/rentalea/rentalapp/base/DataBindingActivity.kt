package cl.rentalea.rentalapp.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class DataBindingActivity<T: ViewDataBinding> : AppCompatActivity(){
    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected lateinit var binding: T
      private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
    }
}