package com.harunturkoglu.cryptoappsample.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harunturkoglu.cryptoappsample.data.model.ui.CryptoCurrencyUiModel
import com.harunturkoglu.cryptoappsample.databinding.ItemCryptocurrencyBinding

class CryptoCurrencyAdapter(
    private val onCryptoSelectedCallback: (CryptoCurrencyUiModel) -> Unit
) : ListAdapter<CryptoCurrencyUiModel, CryptoCurrencyViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoCurrencyViewHolder {
        return CryptoCurrencyViewHolder(
            binding = ItemCryptocurrencyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onCryptoSelectedCallback
        )
    }

    override fun onBindViewHolder(holder: CryptoCurrencyViewHolder, position: Int) {
      holder.bind(getItem(position))
    }

    private object DiffCallback : DiffUtil.ItemCallback<CryptoCurrencyUiModel>() {
        override fun areItemsTheSame(
            oldItem: CryptoCurrencyUiModel,
            newItem: CryptoCurrencyUiModel
        ): Boolean =
            oldItem.currencySummary.currencyId == newItem.currencySummary.currencyId


        override fun areContentsTheSame(
            oldItem: CryptoCurrencyUiModel,
            newItem: CryptoCurrencyUiModel
        ): Boolean =
            oldItem == newItem
    }
}

class CryptoCurrencyViewHolder(
    private val binding: ItemCryptocurrencyBinding,
    onCryptoCurrencySelectedCallback: (CryptoCurrencyUiModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.clRoot.setOnClickListener {
            binding.crypto?.let { cryptoCurrencyUiModel ->
                onCryptoCurrencySelectedCallback.invoke(cryptoCurrencyUiModel)
            }
        }
    }

    fun bind(item: CryptoCurrencyUiModel) {
        binding.crypto = item
    }
}