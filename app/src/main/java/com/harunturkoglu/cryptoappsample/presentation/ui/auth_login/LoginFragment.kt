package com.harunturkoglu.cryptoappsample.presentation.ui.auth_login

import androidx.lifecycle.lifecycleScope
import com.harunturkoglu.cryptoappsample.R
import com.harunturkoglu.cryptoappsample.databinding.FragmentLoginBinding
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
class LoginFragment : BaseFragment<FragmentLoginBinding, MainViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_login

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    @Inject
    lateinit var firebaseAuthenticationManager: FirebaseAuthenticationManager

    override fun initClickListeners() {
        super.initClickListeners()
        with(binding) {
            btnLogin.setOnClickListener {

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
                    firebaseAuthenticationManager.loginUser(
                        email = email, password = password,
                        onResult = this@LoginFragment::onLoginResult
                    )

                }
            }
            textViewRegister.setOnClickListener {
                nav(direction = LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }
    }

    private fun onLoginResult(result: Boolean) {
        if (result)
            nav(LoginFragmentDirections.actionLoginFragmentToHomeNavigation())
        else
            showGeneralDialog(
                descriptionText = getString(R.string.text_login_error),
                isError = true
            )
    }
}