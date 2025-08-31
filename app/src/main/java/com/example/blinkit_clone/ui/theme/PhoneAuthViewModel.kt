package com.example.blinkit_clone.ui.theme

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blinkit_clone.Common.AuthState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PhoneAuthViewModel @Inject constructor() : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState = _authState.asStateFlow()

    private val _isUserLoggedIn = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    private var verificationId: String = ""

    // ✅ THE FIX: Create a listener that actively checks for auth changes.
    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        // This will be called whenever the user signs in or out.
        _isUserLoggedIn.value = firebaseAuth.currentUser != null
    }

    init {
        auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true)
        // ✅ THE FIX: Start listening for auth changes when the ViewModel is created.
        auth.addAuthStateListener(authStateListener)
    }

    // ✅ THE FIX: Stop listening when the ViewModel is destroyed to prevent memory leaks.
    override fun onCleared() {
        super.onCleared()
        auth.removeAuthStateListener(authStateListener)
    }

    fun signOut() {
        auth.signOut()
        // The listener will automatically update _isUserLoggedIn to false.
    }

    fun sendVerificationCode(phoneNumber: String, activity: ComponentActivity) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                _authState.value = AuthState.Error(e.message ?: "Verification failed")
            }

            override fun onCodeSent(
                verId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                verificationId = verId
                _authState.value = AuthState.CodeSent
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(code: String) {
        if (verificationId.isEmpty()) {
            _authState.value = AuthState.Error("Verification ID is empty")
            return
        }
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        viewModelScope.launch {
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = AuthState.Verified
                        // The listener will automatically update _isUserLoggedIn to true.
                    } else {
                        _authState.value =
                            AuthState.Error(task.exception?.message ?: "Authentication failed")
                    }
                }
        }
    }
}
