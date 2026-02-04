package com.pet.cvs360.biometric

data class BiometricState(
    val isLoading: Boolean,
    val error: String?
)

sealed class BiometricEffect {
    data object BiometricSetSuccess: BiometricEffect()
    data object BiometricAuthSuccess: BiometricEffect()
}