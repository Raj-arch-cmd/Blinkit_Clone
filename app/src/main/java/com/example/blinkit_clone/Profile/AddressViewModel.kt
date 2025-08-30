package com.example.blinkit_clone.Profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor() : ViewModel() {

    // Use MutableStateFlow to hold the address data so the UI can observe changes.
    private val _fullName = MutableStateFlow("Raj Singh")
    val fullName = _fullName.asStateFlow()

    private val _streetAddress = MutableStateFlow("Dighi, Alandi Road")
    val streetAddress = _streetAddress.asStateFlow()

    private val _city = MutableStateFlow("Pune")
    val city = _city.asStateFlow()

    private val _state = MutableStateFlow("Maharashtra")
    val state = _state.asStateFlow()

    private val _zipCode = MutableStateFlow("411015")
    val zipCode = _zipCode.asStateFlow()

    private val _phoneNumber = MutableStateFlow("8957132374")
    val phoneNumber = _phoneNumber.asStateFlow()

    // Functions to update the data. These will be called from the UI.
    fun onFullNameChange(newName: String) {
        _fullName.value = newName
    }

    fun onStreetAddressChange(newAddress: String) {
        _streetAddress.value = newAddress
    }

    fun onCityChange(newCity: String) {
        _city.value = newCity
    }

    fun onStateChange(newState: String) {
        _state.value = newState
    }

    fun onZipCodeChange(newZip: String) {
        _zipCode.value = newZip
    }

    fun onPhoneNumberChange(newPhone: String) {
        _phoneNumber.value = newPhone
    }

    fun saveAddress() {
        // In a real app, you would save this data to a database or send it to a server.
        // For now, the data is saved as long as the app is running.
        println("Address Saved!")
    }
}
