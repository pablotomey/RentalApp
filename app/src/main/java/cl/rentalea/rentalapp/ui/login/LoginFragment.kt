package cl.rentalea.rentalapp.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentLoginBinding
import cl.rentalea.rentalapp.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : DataBindingFragment<FragmentLoginBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = this@LoginFragment
        }

        btn_ingresar.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor = Color.TRANSPARENT
    }
}