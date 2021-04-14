package cl.rentalea.rentalapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class DataBindingFragment<T: ViewDataBinding> : Fragment() {
    @LayoutRes
    abstract fun getLayoutRestId(): Int

    protected lateinit var binding: T
        private set

    var nav : NavController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRestId(), container, false)
        nav = NavHostFragment.findNavController(this)
        return binding.root
    }
}