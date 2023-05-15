package com.harunturkoglu.cryptoappsample.presentation.ui.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.harunturkoglu.cryptoappsample.R
import com.harunturkoglu.cryptoappsample.databinding.FragmentSplashBinding
import com.harunturkoglu.cryptoappsample.presentation.base.BaseFragment
import com.harunturkoglu.cryptoappsample.presentation.ui.MainViewModel
import com.harunturkoglu.cryptoappsample.utils.FirebaseAuthenticationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, MainViewModel>() {

    @Inject
    lateinit var firebaseAuthenticationManager: FirebaseAuthenticationManager

    override fun getLayoutRes(): Int = R.layout.fragment_splash

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            delay(SPLASH_SCREEN_DURATION_MILLIS)
            if (firebaseAuthenticationManager.getCurrentUser() != null)
                nav(direction = SplashFragmentDirections.actionSplashFragmentToHomeNavigation())
            else
                nav(direction = SplashFragmentDirections.actionSplashFragmentToAuthNavigation())
        }
    }

    private companion object {
        const val SPLASH_SCREEN_DURATION_MILLIS = 1500L
    }
}