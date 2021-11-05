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
import cl.rentalea.rentalapp.preferences.DataManager
import cl.rentalea.rentalapp.ui.MainActivity
import cl.rentalea.rentalapp.utils.Constants.DLOADING
import cl.rentalea.rentalapp.utils.DialogLoading
import cl.rentalea.rentalapp.utils.validarRut
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.login_input_layout.view.*
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber

class LoginFragment : DataBindingFragment<FragmentLoginBinding>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_login

    val dataManager by lazy { DataManager.getInstance(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginVm = getViewModel()
            lifecycleOwner = this@LoginFragment
        }

        binding.loginInputs.btnIngresar.setOnClickListener {

            when {
                binding.loginInputs.rutInput.text.isNullOrEmpty() -> showInfoDialog("Debe ingresar su rut.")
                !validarRut(binding.loginInputs.rutInput.text.toString()) -> showInfoDialog("Debe ingresar un rut valido.")
                binding.loginInputs.passInput.text.isNullOrEmpty() -> showInfoDialog("Debe Ingresar su contraseña.")
                else -> {
                    getUserObserve(binding.loginInputs.rutInput.text.toString(), binding.loginInputs.passInput.text.toString())
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
                    dataManager.createUserSession(response.data.nombre, response.data.rut)
                    getVehiculosListObserve()
                    Timber.e("${response.data}")
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

    private fun getVehiculosListObserve() {
        binding.loginVm?.obtenerListaVehiculos()?.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Respuesta.Loading -> showProgressBar(true)
                is Respuesta.Success -> {
                    showProgressBar(false)
                    binding.loginVm?.eliminarVehiculos()
                    for (vehiculo in response.data) {
                        binding.loginVm?.guardarVehiculo(vehiculo)
                    }
                    getEquiposListObserve()
                    Timber.e("${response.data}")
                }
                is Respuesta.Failure -> {
                    showProgressBar(false)
                    showInfoDialog(response.exception)
                    Timber.e("ERROR -> ${response.exception}")
                }
            }
        })
    }

    private fun getEquiposListObserve() {
        binding.loginVm?.obtenerListaEquipos()?.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Respuesta.Loading -> showProgressBar(true)
                is Respuesta.Success -> {
                    showProgressBar(false)
                    binding.loginVm?.eliminarEquipos()
                    for (equipo in response.data) {
                        binding.loginVm?.guardarEquipo(equipo)
                    }
                    getObrasListObserve()
                    Timber.e("${response.data}")
                }
                is Respuesta.Failure -> {
                    showProgressBar(false)
                    showInfoDialog(response.exception)
                    Timber.e("ERROR -> ${response.exception}")
                }
            }
        })
    }

    private fun getObrasListObserve() {
        binding.loginVm?.obtenerListaObras()?.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Respuesta.Loading -> showProgressBar(true)
                is Respuesta.Success -> {
                    showProgressBar(false)
                    for (obra in response.data) {
                        binding.loginVm?.guardarObra(obra)
                    }
                    getEmpresasListObserve()
                    Timber.e("${response.data}")
                }
                is Respuesta.Failure -> {
                    showProgressBar(false)
                    showInfoDialog(response.exception)
                    Timber.e("ERROR -> ${response.exception}")
                }
            }
        })
    }

    private fun getEmpresasListObserve() {
        binding.loginVm?.obtenerListaEmpresas()?.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Respuesta.Loading -> showProgressBar(true)
                is Respuesta.Success -> {
                    showProgressBar(false)
                    for (empresa in response.data) {
                        binding.loginVm?.guardarEmpresa(empresa)
                    }
                    getMaterialesListObserve()
                    Timber.e("${response.data}")
                }
                is Respuesta.Failure -> {
                    showProgressBar(false)
                    showInfoDialog(response.exception)
                    Timber.e("ERROR -> ${response.exception}")
                }
            }
        })
    }

    private fun getMaterialesListObserve() {
        binding.loginVm?.obtenerListaMateriales()?.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Respuesta.Loading -> showProgressBar(true)
                is Respuesta.Success -> {
                    showProgressBar(false)
                    for (material in response.data) {
                        binding.loginVm?.guardarMaterial(material)
                    }
                    getAditamentosListObserve()
                    Timber.e("${response.data}")
                }
                is Respuesta.Failure -> {
                    showProgressBar(false)
                    showInfoDialog(response.exception)
                    Timber.e("ERROR -> ${response.exception}")
                }
            }
        })
    }

    private fun getAditamentosListObserve() {
        binding.loginVm?.obtenerListaAditamentos()?.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Respuesta.Loading -> showProgressBar(true)
                is Respuesta.Success -> {
                    showProgressBar(false)
                    for (aditamento in response.data) {
                        binding.loginVm?.guardarAditamento(aditamento)
                    }
                    getAccesoriosListObserve()
                    Timber.e("${response.data}")
                }
                is Respuesta.Failure -> {
                    showProgressBar(false)
                    showInfoDialog(response.exception)
                    Timber.e("ERROR -> ${response.exception}")
                }
            }
        })
    }

    private fun getAccesoriosListObserve() {
        binding.loginVm?.obtenerListaAccesorios()?.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Respuesta.Loading -> showProgressBar(true)
                is Respuesta.Success -> {
                    showProgressBar(false)
                    for (accesorio in response.data) {
                        binding.loginVm?.guardarAccesorio(accesorio)
                    }
                    getCheckListItemsObserve()
                    Timber.e("${response.data}")
                }
                is Respuesta.Failure -> {
                    showProgressBar(false)
                    showInfoDialog(response.exception)
                    Timber.e("ERROR -> ${response.exception}")
                }
            }
        })
    }

    private fun getCheckListItemsObserve() {
        binding.loginVm?.obtenerCheckListItems()?.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Respuesta.Loading -> showProgressBar(true)
                is Respuesta.Success -> {
                    showProgressBar(false)
                    for (checkListItem in response.data) {
                        binding.loginVm?.guardarCheckListItem(checkListItem)
                    }
                    goToMainActivity()
                    Timber.e("${response.data}")
                }
                is Respuesta.Failure -> {
                    showProgressBar(false)
                    showInfoDialog(response.exception)
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