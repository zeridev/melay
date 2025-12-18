package eu.dezeekees.melay.common.payload.user

interface UserResult {
    val id: String?
    val username: String
    val displayName: String
    var profilePicture: String?
    var profileDescription: String?
    val createdAt: String?
}