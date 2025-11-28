package eu.dezeekees.melay.app.data.util

import eu.dezeekees.melay.app.logic.`interface`.Crypto

expect object Crypto: Crypto {
    override fun encrypt(bytes: ByteArray): ByteArray
    override fun decrypt(bytes: ByteArray): ByteArray
}