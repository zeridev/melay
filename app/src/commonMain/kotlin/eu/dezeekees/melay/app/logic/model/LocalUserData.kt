package eu.dezeekees.melay.app.logic.model

import kotlinx.serialization.Serializable

@Serializable
data class LocalUserData(
    val remoteDomain: String = ""
)