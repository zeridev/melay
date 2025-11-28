package eu.dezeekees.melay.app.data.util

import eu.dezeekees.melay.app.logic.`interface`.Crypto
import okio.FileSystem
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

actual object Crypto : Crypto {
    private const val ALGORITHM = "AES"
    private const val BLOCK_MODE = "CBC"
    private const val PADDING = "PKCS5Padding"
    private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    private val keyFile = ConfigDir.getFile("keyfile.enc")
    private val cipher = Cipher.getInstance(TRANSFORMATION)
    private val key: SecretKey = loadOrCreateKey()

    private fun loadOrCreateKey(): SecretKey {
        if (FileSystem.SYSTEM.exists(keyFile)) {
           val bytes = FileSystem.SYSTEM.read(keyFile) {
               readByteArray()
           }
            return SecretKeySpec(bytes, ALGORITHM)
        }

        val keyGen = KeyGenerator.getInstance(ALGORITHM)
        keyGen.init(256)
        val secretKey = keyGen.generateKey()

        FileSystem.SYSTEM.write(keyFile) {
            write(secretKey.encoded)
        }
        return secretKey
    }

    actual override fun encrypt(bytes: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(bytes)
        return iv + encrypted
    }

    actual override fun decrypt(bytes: ByteArray): ByteArray {
        val ivSize = cipher.blockSize
        val iv = bytes.copyOfRange(0, ivSize)
        val data = bytes.copyOfRange(ivSize, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        return cipher.doFinal(data)
    }
}