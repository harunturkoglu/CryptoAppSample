package com.harunturkoglu.cryptoappsample.ext

fun Int?.orElse(value: Int = 0) = this ?: value

val Int.Companion.INVALID_ID get() = -1
