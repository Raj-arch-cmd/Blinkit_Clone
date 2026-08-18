package com.example.blinkit_clone.presentation.screens.auth

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blinkit_clone.Common.AuthState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    // ✅ THE FIX: One-shot event for explicit logout
    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent = _logoutEvent.asSharedFlow()

    private var verificationId: String? = null

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        _isUserLoggedIn.value = auth.currentUser != null
    }

    fun sendVerificationCode(phoneNumber: String, activity: Activity) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                _authState.value = AuthState.Error(e.message ?: "Verification failed")
            }

            override fun onCodeSent(verId: String, token: PhoneAuthProvider.ForceResendingToken) {
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
        if (verificationId.isNullOrEmpty()) {
            _authState.value = AuthState.Error("Verification ID is missing")
            return
        }
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = AuthState.Verified
                        _isUserLoggedIn.value = true
                    } else {
                        _authState.value = AuthState.Error(task.exception?.message ?: "Sign-in failed")
                    }
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    fun signOut() {
        Log.d("PhoneAuthViewModel", "SIGNOUT_CALLED")
        auth.signOut()
        _isUserLoggedIn.value = false
        _authState.value = AuthState.Initial
        
        // ✅ Emit one-shot logout event
        viewModelScope.launch {
            Log.d("PhoneAuthViewModel", "LOGOUT_EVENT_EMITTED")
            _logoutEvent.emit(Unit)
        }
    }
}
