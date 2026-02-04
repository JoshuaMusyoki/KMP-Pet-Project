package com.pet.cvs360.biometric

interface ICipherUtil {
    @Throws(Exception::class)
    fun generateKeyPair(): CommonPair

    fun getPublicKey(): CommonPublicKey

    @Throws(Exception::class)
    fun getCrypto(): Crypto

    @Throws(Exception::class)
    suspend fun removePublicKey()
}

expect class CommonPair
expect interface CommonPublicKey
expect class Crypto