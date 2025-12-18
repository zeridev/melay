package eu.dezeekees.melay.app.logic.repository

import eu.dezeekees.melay.app.logic.model.LocalUserData

interface UserDataStoreRepository {
    suspend fun set(userData: LocalUserData)
    suspend fun get(): LocalUserData?
}