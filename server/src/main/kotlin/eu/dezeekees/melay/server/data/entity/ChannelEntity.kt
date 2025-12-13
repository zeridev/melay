package eu.dezeekees.melay.server.data.entity

import eu.dezeekees.melay.server.logic.model.ChannelType
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.UUID

object Channels : UUIDTable("channels") {
    val name = varchar("name", 100)
    val type = enumerationByName("type", 10, ChannelType::class)
    val communityId = reference("community_id", Communities)
    val position = integer("position")
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
}

class ChannelEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ChannelEntity>(Channels)

    var name by Channels.name
    var type by Channels.type
    var position by Channels.position
    var communityId by Channels.communityId
    var createdAt by Channels.createdAt
}