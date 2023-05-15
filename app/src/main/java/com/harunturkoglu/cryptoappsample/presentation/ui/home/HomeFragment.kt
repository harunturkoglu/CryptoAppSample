package com.harunturkoglu.cryptoappsample.presentation.ui.home

import androidx.core.widget.doOnTextChanged
import com.harunturkoglu.cryptoappsample.R
import com.harunturkoglu.cryptoappsample.data.model.ui.CryptoCurrencyUiModel
import com.harunturkoglu.cryptoappsample.databinding.FragmentHomeBinding
import com.harunturkoglu.cryptoappsample.ext.observe
import com.harunturkoglu.cryptoappsample.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    private val  cryptoCurrencyAdapter: CryptoCurrencyAdapter by lazy {
        CryptoCurrencyAdapter(this::onCryptoCurrencySelected)
    }

    override fun initUi() {
        super.initUi()
        binding.rvCryptocurrencies.adapter = cryptoCurrencyAdapter
        viewModel.getLatest()
    }

    override fun initClickListeners() {
        super.initClickListeners()
        binding.imageViewSearch.setOnClickListener {
            val queryText = binding.editTextSearch.text.toString()
            refreshUiWithSearch(queryText = queryText)
        }
        binding.editTextSearch.doOnTextChanged { text, _, _, _ ->
            refreshUiWithSearch(queryText = text.toString())
        }
    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.getListingLatestData) { result ->
            cryptoCurrencyAdapter.submitList(result)
        }
    }

    fun onCryptoCurrencySelected(item: CryptoCurrencyUiModel) {
        nav(direction = HomeFragmentDirections.actionHomeFragmentToCryptoDetailFragment(item.currencySummary.currencyId.toString()))
    }

    private fun refreshUiWithSearch(queryText: String) {
        val newList = viewModel.getListingLatestData.value?.filter { s -> s.currencySummary.currencyNameSymbol.contains(queryText) }
        cryptoCurrencyAdapter.submitList(newList)
        binding.rvCryptocurrencies.smoothScrollToPosition(0)
    }
}