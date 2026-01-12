package eu.dezeekees.melay.app.logic.service

import eu.dezeekees.melay.app.logic.model.auth.Token
import eu.dezeekees.melay.app.logic.repository.TokenStoreRepository

class TokenService(
    private val tokenStoreRepository: TokenStoreRepository
) {
    @Volatile
    private var cachedToken: Token? = null

    suspend fun tokenIsSet(): Boolean {
        val token = tokenStoreRepository.get() ?: return false
        return token.token.isNotBlank()
    }

    suspend fun getToken(): Token? {
        return cachedToken ?: run {
            val token = tokenStoreRepository.get()
            cachedToken
            token
        }
    }

    suspend fun deleteToken() {
        tokenStoreRepository.set(Token(""))
    }
}