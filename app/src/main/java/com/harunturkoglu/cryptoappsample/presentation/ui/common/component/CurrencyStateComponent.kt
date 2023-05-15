package com.harunturkoglu.cryptoappsample.presentation.ui.common.component

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.harunturkoglu.cryptoappsample.R
import com.harunturkoglu.cryptoappsample.databinding.ComponentCurrencyStateBinding
import com.harunturkoglu.cryptoappsample.ext.formatToUi
import com.harunturkoglu.cryptoappsample.ext.orElse

class CurrencyStateComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ComponentCurrencyStateBinding

    init {
        binding = ComponentCurrencyStateBinding.inflate(LayoutInflater.from(context), this, true)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CurrencyStateComponent,
            defStyleAttr,
            0
        )

        setUI(typedArray)
    }

    private fun setUI(typedArray: TypedArray) {

        //Set Title
        val currencyTitle =
            typedArray.getString(R.styleable.CurrencyStateComponent_currencyTitle).orElse()
        setTitleText(currencyTitle)

        // Set Description
        val descriptionValue =
            typedArray.getString(R.styleable.CurrencyStateComponent_currencyDescription).orElse()

        //If param showCurrencyStateByDescriptionValue is true and  description value contains a numeral value, then set the drawable by current state.
        if (typedArray.getBoolean(
                R.styleable.CurrencyStateComponent_showCurrencyStateByDescriptionValue,
                false
            )
        ) updateImageViewState(descriptionValue)
        else
            setDescriptionText(descriptionValue)
    }

    private fun updateImageViewState(value: String) {
        value.toDoubleOrNull()?.let {
            setDescriptionText(it.formatToUi().orElse())
            if (it > 0) {
                binding.textViewDescription.setTextColor(context.resources.getColor(R.color.jungle_green))
                binding.imageViewState.setImageResource(R.drawable.ic_arrow_up)
            } else {
                binding.textViewDescription.setTextColor(context.resources.getColor(R.color.lava))
                binding.imageViewState.setImageResource(R.drawable.ic_arrow_down)
            }
        }

        value.toIntOrNull()?.let {
            if (it > 0) {
                binding.textViewDescription.setTextColor(context.resources.getColor(R.color.jungle_green))
                binding.imageViewState.setImageResource(R.drawable.ic_arrow_up)
            } else {
                binding.textViewDescription.setTextColor(context.resources.getColor(R.color.lava))
                binding.imageViewState.setImageResource(R.drawable.ic_arrow_down)
            }
        }
    }

    fun setTitleText(title: String) {
        binding.tvTitle.text = title
    }

    fun setDescriptionText(description: String) {
        binding.textViewDescription.text = description
        updateImageViewState(description)
    }
}