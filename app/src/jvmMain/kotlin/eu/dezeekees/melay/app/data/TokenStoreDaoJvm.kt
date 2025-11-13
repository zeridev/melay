package eu.dezeekees.melay.app.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import eu.dezeekees.melay.app.data.util.ConfigDir
import eu.dezeekees.melay.app.data.util.TokenSerializer
import eu.dezeekees.melay.app.logic.model.auth.Token
import eu.dezeekees.melay.app.logic.repository.TokenStoreRepository
import kotlinx.coroutines.flow.firstOrNull
import java.io.File

class TokenStoreDaoJvm : TokenStoreRepository {
    private val dataStore: DataStore<Token> = DataStoreFactory.create(
        serializer = TokenSerializer,
        produceFile = {
            val configDir = ConfigDir.getConfigDir()
            File(configDir.toFile(), "token.preferences_pb")
        }
    )

    override suspend fun set(token: Token) {
        dataStore.updateData { token }
    }

    override suspend fun get(): Token? {
        return dataStore.data.firstOrNull()
    }
}
