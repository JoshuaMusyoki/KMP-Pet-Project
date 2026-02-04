package com.pet.cvs360.biometric

import kotlinx.coroutines.delay

private var publicKeyOnServer = ""
class SetBiometricPublicKeyRepository {
    suspend fun setBiometricPublicKey(publicKey: String) {
        delay(500)
        publicKeyOnServer = publicKey
    }
}

class VerifyBiometric {
    suspend fun verifyBiometricPublicKey(signedUserId: String): Result<Unit> {
        return Result.success(Unit)
    }
}