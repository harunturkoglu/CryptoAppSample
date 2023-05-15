package com.harunturkoglu.cryptoappsample.utils

import androidx.navigation.NavOptions
import com.harunturkoglu.cryptoappsample.R

object NavAnimOptions {

    fun getNavOptionsSlide(navOptions: NavOptions? = null) = kotlin.run {
        NavOptions.Builder()
            .setEnterAnim(R.anim.anim_slide_enter)
            .setExitAnim(R.anim.anim_slide_exit)
            .setPopEnterAnim(R.anim.anim_slide_pop_enter)
            .setPopExitAnim(R.anim.anim_slide_pop_exit).also {
                navOptions?.run {
                    it.setPopUpTo(this.popUpToId, this.isPopUpToInclusive())
                    it.setLaunchSingleTop(this.shouldLaunchSingleTop())
                }
            }.build()
    }
}
