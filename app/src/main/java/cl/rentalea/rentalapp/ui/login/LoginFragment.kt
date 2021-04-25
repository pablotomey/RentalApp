package cl.rentalea.rentalapp.ui.login

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentLoginBinding

class LoginFragment : DataBindingFragment<FragmentLoginBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = this@LoginFragment
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor = Color.TRANSPARENT
    }
}