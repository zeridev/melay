package eu.dezeekees.melay.app.data.util

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class JsonDataStoreSerializer<T>(
    private val serializer: KSerializer<T>,
    override val defaultValue: T
) : Serializer<T> {

    override suspend fun readFrom(input: InputStream): T {
        return try {
            Json.decodeFromString(serializer, input.readBytes().decodeToString())
        } catch (e: Exception) {
            throw CorruptionException("Unable to read JSON", e)
        }
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        val jsonString = Json.encodeToString(serializer, t)
        output.write(jsonString.toByteArray())
    }
}