package eu.dezeekees.melay.common.payload.community

interface CreateCommunityCommand {
    val name: String
    val description: String
}