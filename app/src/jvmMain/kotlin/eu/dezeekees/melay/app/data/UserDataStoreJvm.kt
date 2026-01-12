package eu.dezeekees.melay.app.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import eu.dezeekees.melay.app.data.util.AppDirs
import eu.dezeekees.melay.app.data.util.JsonDataStoreSerializer
import eu.dezeekees.melay.app.logic.model.LocalUserData
import eu.dezeekees.melay.app.logic.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.firstOrNull
import java.io.File

class UserDataStoreJvm : UserDataStoreRepository {
    private val dataStore: DataStore<LocalUserData> = DataStoreFactory.create(
        serializer = JsonDataStoreSerializer(LocalUserData.serializer(), LocalUserData()),
        produceFile = {
            val configDir = AppDirs.configDir()
            File(configDir.toFile(), "user_data.json")
        }
    )

    override suspend fun set(userData: LocalUserData) {
        dataStore.updateData { userData }
    }

    override suspend fun get(): LocalUserData? {
        return dataStore.data.firstOrNull()
    }
}