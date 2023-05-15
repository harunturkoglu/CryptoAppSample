package com.harunturkoglu.cryptoappsample.presentation.ui.common.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import com.harunturkoglu.cryptoappsample.R
import com.harunturkoglu.cryptoappsample.databinding.DialogGeneralBinding
import com.harunturkoglu.cryptoappsample.ext.orElse

class GeneralDialog(context: Context) : AlertDialog(context) {

    val binding: DialogGeneralBinding by lazy {
        DialogGeneralBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        setContentView(binding.root)
    }


    @SuppressLint("ResourceType")
    fun prepareAndShow(
        descriptionText: String,
        isError: Boolean,
        titleText: String? = null,
        positiveButtonText: String? = null,
        negativeButtonText: String? = null,
        positiveButtonCallback: (() -> Unit)? = null,
        negativeButtonCallback: (() -> Unit)? = null,
    ) {
        binding.attrs = GeneralButtonAttrs(
            isPositiveButtonVisible = !positiveButtonText.isNullOrEmpty(),
            isNegativeButtonVisible = !negativeButtonText.isNullOrEmpty(),
            positiveButtonText = negativeButtonText.orElse(context.getString(R.string.btn_ok)),
            negativeButtonText = negativeButtonText.orElse(context.getString(R.string.btn_cancel)),
            imageResId = if (isError) R.drawable.ic_error else R.drawable.ic_success,
            isTitleVisible = titleText.isNullOrEmpty(),
            titleText = titleText.orElse(),
            descriptionText = descriptionText
        )

        binding.btnPositive.setOnClickListener {
            positiveButtonCallback?.invoke()
            dismiss()
        }
        binding.btnNegative.setOnClickListener {
            negativeButtonCallback?.invoke()
            dismiss()
        }
        show()
    }
}

data class GeneralButtonAttrs(
    val isPositiveButtonVisible: Boolean,
    val isNegativeButtonVisible: Boolean,
    val positiveButtonText: String,
    val negativeButtonText: String,
    @IdRes val imageResId: Int,
    val isTitleVisible: Boolean,
    val titleText: String,
    val descriptionText: String,
)
