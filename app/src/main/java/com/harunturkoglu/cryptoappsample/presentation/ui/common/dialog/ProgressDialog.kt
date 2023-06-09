package com.harunturkoglu.cryptoappsample.presentation.ui.common.dialog

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.harunturkoglu.cryptoappsample.R

class ProgressDialog(private val activity: Activity) {

    var dialog: AlertDialog? = null

    fun startDialog() {
        val mDialogView =
            LayoutInflater.from(activity).inflate(R.layout.progress_view, null)
        val mBuilder = AlertDialog.Builder(activity)
            .setView(mDialogView)
        dialog = mBuilder.show()
        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                activity,
                R.drawable.bg_transparent_view
            )
        )
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
    }

    fun dismissDialog() {
        if (dialog != null)
            dialog?.dismiss()
    }
}