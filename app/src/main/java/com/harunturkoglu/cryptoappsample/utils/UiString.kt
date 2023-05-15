package com.harunturkoglu.cryptoappsample.utils

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import com.harunturkoglu.cryptoappsample.ext.EMPTY
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class UiString(
    @StringRes private val resId: Int,
    private vararg val args: @RawValue Any
) : Parcelable {
    fun get(context: Context) = if (resId == INVALID_RES_ID)
        String.EMPTY
    else
        context.getString(resId, *args)

    private companion object {
        const val INVALID_RES_ID = 0
    }
}