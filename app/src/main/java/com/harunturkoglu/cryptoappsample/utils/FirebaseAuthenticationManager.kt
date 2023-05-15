package com.harunturkoglu.cryptoappsample.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harunturkoglu.cryptoappsample.data.local.LocalDataManager
import com.harunturkoglu.cryptoappsample.ext.orElse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthenticationManager @Inject constructor(
    private val localDataManager: LocalDataManager
) {
    private val auth: FirebaseAuth = Firebase.auth

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    suspend fun registerUser(email: String, password: String, onResult : (Boolean) -> Unit) {
        withContext(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                onResult.invoke(it.isSuccessful)
            }
        }
    }

    suspend fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        return withContext(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
                onResult.invoke(authResult.isSuccessful).also {
                    if(authResult.isSuccessful) localDataManager.setCurrentUserEmail(authResult.result.user?.email.orElse())
                }
            }
        }
    }
}