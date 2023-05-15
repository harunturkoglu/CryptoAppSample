package com.harunturkoglu.cryptoappsample.ext

fun String?.orElse(value: String = String.EMPTY) = this ?: value

val String.Companion.EMPTY get() = ""

