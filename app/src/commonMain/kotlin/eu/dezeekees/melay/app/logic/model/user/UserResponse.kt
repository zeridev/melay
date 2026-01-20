package eu.dezeekees.melay.app.logic.model.user

import eu.dezeekees.melay.common.payload.user.UserResult
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    override val id: String = "",
    override val username: String = "",
    override val displayName: String = "",
    override var profilePicture: String? = null,
    override var profileDescription: String? = null,
    override val createdAt: String,
) : UserResult
