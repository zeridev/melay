package eu.dezeekees.melay.app.data.util

import androidx.datastore.core.Serializer
import eu.dezeekees.melay.app.logic.model.auth.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import kotlin.io.encoding.Base64

object TokenSerializer: Serializer<Token> {
    override val defaultValue: Token
        get() = Token("")

    override suspend fun readFrom(input: InputStream): Token {
        val encryptedBase64Bytes = withContext(Dispatchers.IO) {
            input.use { it.readBytes() }
        }
        val encryptedBytes = Base64.decode(encryptedBase64Bytes)
        val decryptedBytes = Crypto.decrypt(encryptedBytes)
        val jsonString = decryptedBytes.decodeToString()
        return Json.decodeFromString(jsonString)
    }

    override suspend fun writeTo(t: Token, output: OutputStream) {
        val jsonBytes = Json.encodeToString(t)
            .toByteArray()
        val encryptedBytes = Crypto.encrypt(jsonBytes)
        val encryptedBase64Bytes = Base64.encodeToByteArray(encryptedBytes)
        withContext(Dispatchers.IO) {
            output.use {
                it.write(encryptedBase64Bytes)
            }
        }
    }
}