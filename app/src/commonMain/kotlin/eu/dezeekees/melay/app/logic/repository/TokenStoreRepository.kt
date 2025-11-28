package eu.dezeekees.melay.app.logic.repository

import eu.dezeekees.melay.app.logic.model.auth.Token

interface TokenStoreRepository {
    suspend fun set(token: Token)
    suspend fun get(): Token?
}