package com.harunturkoglu.cryptoappsample.presentation.ui.detail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.harunturkoglu.cryptoappsample.R
import com.harunturkoglu.cryptoappsample.databinding.FragmentCryptoDetailBinding
import com.harunturkoglu.cryptoappsample.ext.hideKeyboard
import com.harunturkoglu.cryptoappsample.ext.observe
import com.harunturkoglu.cryptoappsample.ext.orElse
import com.harunturkoglu.cryptoappsample.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CryptoDetailFragment : BaseFragment<FragmentCryptoDetailBinding, CryptoDetailViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_crypto_detail

    override fun getViewModelClass(): Class<CryptoDetailViewModel> =
        CryptoDetailViewModel::class.java

    private val args: CryptoDetailFragmentArgs by navArgs()

    private val id
        get() = args.id

    override fun initUi() {
        super.initUi()
        viewModel.id = id.toIntOrNull().orElse()
        startRefreshingUi()
    }

    private fun startRefreshingUi() {
        lifecycleScope.launch{
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
               while (true){
                   viewModel.getLatest()
                   delay(viewModel.getRefreshIntervalValue())
               }
            }
        }
    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.getQuotesLatestData) {
            binding.detail = it
        }
    }

    override fun initClickListeners() {
        super.initClickListeners()

        binding.imageViewRefreshInternalQuestionMark.setOnClickListener {
            showGeneralDialog(descriptionText = getString(R.string.text_detail_refresh_internal_description))
        }

        binding.imageApprove.setOnClickListener {
            val interval = binding.editTextRefreshIntervalMillis.text.toString().toLongOrNull()
            binding.imageApprove.hideKeyboard()
            if (interval != null && interval.orElse() > MIN_REFRESH_INTERVAL_MILLIS) {
                viewModel.setRefreshIntervalValue(interval)
            } else {
                showGeneralDialog(
                    descriptionText = getString(R.string.text_detail_refresh_internal_error),
                    isError = true
                )
            }
        }
    }

    private companion object {
        const val MIN_REFRESH_INTERVAL_MILLIS = 500L
    }
}