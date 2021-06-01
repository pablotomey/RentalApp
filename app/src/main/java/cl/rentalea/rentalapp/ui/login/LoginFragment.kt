package cl.rentalea.rentalapp.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import cl.rentalea.rentalapp.R
import cl.rentalea.rentalapp.base.Respuesta
import cl.rentalea.rentalapp.binding.DataBindingFragment
import cl.rentalea.rentalapp.databinding.FragmentLoginBinding
import cl.rentalea.rentalapp.ui.MainActivity
import cl.rentalea.rentalapp.utils.Constants.DLOADING
import cl.rentalea.rentalapp.utils.Constants.OPERATOR
import cl.rentalea.rentalapp.utils.DialogLoading
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber

class LoginFragment : DataBindingFragment<FragmentLoginBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginVm = getViewModel()
            lifecycleOwner = this@LoginFragment
        }

        btn_ingresar.setOnClickListener {

            when {
                rut_input.text.isNullOrEmpty() -> showInfoDialog("Debe ingresar su rut.")
                pass_input.text.isNullOrEmpty() -> showInfoDialog("Debe Ingresar su contraseña.")
                else -> {
                    getUserObserve(rut_input.text.toString(), pass_input.text.toString())
                }
            }
        }
    }

    private fun getUserObserve(rut: String, password: String) {
        binding.loginVm?.obtenerUsuario(rut, password)?.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Respuesta.Loading -> showProgressBar(true)
                is Respuesta.Success -> {
                    showProgressBar(false)
                    binding.loginVm?.guardarUsuario(response.data)
                    OPERATOR = response.data
                    goToMainActivity()
                    Timber.e("USER = ${response.data}")
                }
                is Respuesta.Failure -> {
                    showProgressBar(false)
                    if (response.exception == "null") showInfoDialog("El Usuario no esta registrado, revise sus datos e intente nuevamente.")
                    else showInfoDialog(response.exception)
                    Timber.e("ERROR -> ${response.exception}")
                }
            }
        })
    }

    private fun goToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun showProgressBar(value: Boolean) {
        binding.loginVm?.isLoading?.postValue(value)
    }

    private fun showInfoDialog(msg: String) {
        binding.loginVm?.hasError?.postValue(msg)
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor = Color.TRANSPARENT
        DLOADING = DialogLoading(requireContext(), "Autentificando...")
    }
}