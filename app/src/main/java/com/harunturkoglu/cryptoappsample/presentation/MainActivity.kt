package com.harunturkoglu.cryptoappsample.presentation


import com.harunturkoglu.cryptoappsample.R
import com.harunturkoglu.cryptoappsample.databinding.ActivityMainBinding
import com.harunturkoglu.cryptoappsample.presentation.base.BaseActivity
import com.harunturkoglu.cryptoappsample.presentation.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun getLayoutRes(): Int = R.layout.activity_main
    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
}