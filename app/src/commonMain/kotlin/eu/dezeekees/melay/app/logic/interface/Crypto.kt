package eu.dezeekees.melay.app.logic.`interface`

interface Crypto {
    fun encrypt(bytes: ByteArray): ByteArray
    fun decrypt(bytes: ByteArray): ByteArray
}