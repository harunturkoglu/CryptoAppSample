package com.harunturkoglu.cryptoappsample.utils

import android.util.Patterns

object ValidationBehaviors {
    /**
     * Validate given email parameter by Regex
     */
    fun isEmailValid(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    /**
     * Validate given password value by length which must be non-empty and character length must be between
     * @param[MIN_PASSWORD_CHARACTER_LENGTH] and @param[MAX_PASSWORD_CHARACTER_LENGTH]
     */
    fun isPasswordValid(password: String): Boolean =
        password.isNotEmpty() && (password.length in MIN_PASSWORD_CHARACTER_LENGTH..MAX_PASSWORD_CHARACTER_LENGTH)

}