package eu.dezeekees.melay.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import eu.dezeekees.melay.app.data.util.TokenSerializer
import eu.dezeekees.melay.app.logic.model.auth.Token
import eu.dezeekees.melay.app.logic.repository.TokenStoreRepository
import kotlinx.coroutines.flow.firstOrNull

class TokenStoreDaoAndroid(context: Context) : TokenStoreRepository {
    private val dataStore: DataStore<Token> = DataStoreFactory.create(
        serializer = TokenSerializer,
        produceFile = {
            context.preferencesDataStoreFile("token")
        }
    )

    override suspend fun set(token: Token) {
        dataStore.updateData { token }
    }

    override suspend fun get(): Token? {
        return dataStore.data.firstOrNull()
    }
}