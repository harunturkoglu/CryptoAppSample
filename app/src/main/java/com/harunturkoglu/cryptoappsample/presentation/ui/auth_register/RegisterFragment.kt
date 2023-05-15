package com.harunturkoglu.cryptoappsample.presentation.ui.auth_register

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.harunturkoglu.cryptoappsample.R
import com.harunturkoglu.cryptoappsample.databinding.FragmentRegisterBinding
import com.harunturkoglu.cryptoappsample.presentation.base.BaseFragment
import com.harunturkoglu.cryptoappsample.presentation.ui.MainViewModel
import com.harunturkoglu.cryptoappsample.utils.FirebaseAuthenticationManager
import com.harunturkoglu.cryptoappsample.utils.MAX_PASSWORD_CHARACTER_LENGTH
import com.harunturkoglu.cryptoappsample.utils.MIN_PASSWORD_CHARACTER_LENGTH
import com.harunturkoglu.cryptoappsample.utils.ValidationBehaviors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, MainViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_register

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    @Inject
    lateinit var firebaseAuthenticationManager: FirebaseAuthenticationManager

    override fun initClickListeners() {
        super.initClickListeners()
        with(binding) {
            btnSignUp.setOnClickListener {

                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString().trim()

                if (!ValidationBehaviors.isEmailValid(email)) {
                    showGeneralDialog(
                        descriptionText = getString(R.string.text_email_format_error),
                        isError = true
                    )
                    return@setOnClickListener
                }

                if (!ValidationBehaviors.isPasswordValid(password)) {
                    showGeneralDialog(
                        descriptionText = getString(
                            R.string.text_password_format_error,
                            MIN_PASSWORD_CHARACTER_LENGTH,
                            MAX_PASSWORD_CHARACTER_LENGTH
                        ), isError = true
                    )
                    return@setOnClickListener
                }

                lifecycleScope.launch {
                    firebaseAuthenticationManager.registerUser(
                        email = email,
                        password = password,
                        onResult = this@RegisterFragment::onRegisterComplete
                    )
                }
            }
        }
    }

    private fun onRegisterComplete(result: Boolean) {
        if (result)
            showGeneralDialog(
                descriptionText = getString(R.string.text_register_successful),
                positiveButtonText = getString(R.string.text_login_button),
                positiveButtonCallback = {
                   if(firebaseAuthenticationManager.getCurrentUser() != null)
                       nav(direction = RegisterFragmentDirections.actionRegisterFragmentToHomeNavigation())
                    else
                        findNavController().popBackStack()
                })
        else
            showGeneralDialog(
                descriptionText = getString(R.string.unexpected_error),
                isError = true
            )

    }
}