package eu.dezeekees.melay.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import eu.dezeekees.melay.app.data.util.JsonDataStoreSerializer
import eu.dezeekees.melay.app.logic.model.LocalUserData
import eu.dezeekees.melay.app.logic.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.firstOrNull

class UserDataStoreAndroid(context: Context) : UserDataStoreRepository {
    private val dataStore: DataStore<LocalUserData> = DataStoreFactory.create(
        serializer = JsonDataStoreSerializer(LocalUserData.serializer(), LocalUserData()),
        produceFile = {
            context.preferencesDataStoreFile("token")
        }
    )

    override suspend fun set(userData: LocalUserData) {
        dataStore.updateData { userData }
    }

    override suspend fun get(): LocalUserData? {
        return dataStore.data.firstOrNull()
    }
}