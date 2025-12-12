package eu.dezeekees.melay.server.data.entity

import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.CompositeIdTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object UserCommunityMemberships : CompositeIdTable("user_community_membership") {
    val userId = reference("user_id", Users.id, onDelete = ReferenceOption.CASCADE)
    val communityId = reference("community_id", Communities.id, onDelete = ReferenceOption.CASCADE)
    val joinedAt = datetime("joined_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(userId, communityId)
}


class UserCommunityMembershipEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<UserCommunityMembershipEntity>(UserCommunityMemberships)

    var user by UserEntity referencedOn UserCommunityMemberships.userId
    var community by CommunityEntity referencedOn UserCommunityMemberships.communityId
    var joinedAt by UserCommunityMemberships.joinedAt
}

