package eu.dezeekees.melay.app.logic.repository

import eu.dezeekees.melay.app.logic.model.auth.Token
import kotlinx.coroutines.flow.Flow

interface TokenStoreRepository {
    suspend fun set(token: Token)
    suspend fun get(): Token?

    val flow: Flow<Token?>
}