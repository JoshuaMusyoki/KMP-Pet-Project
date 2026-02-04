package com.pet.cvs360.biometric

import androidx.biometric.BiometricPrompt
import java.security.KeyPair
import java.security.PublicKey

actual typealias CommonPair = KeyPair
actual typealias CommonPublicKey = PublicKey
actual typealias Crypto = BiometricPrompt.CryptoObject