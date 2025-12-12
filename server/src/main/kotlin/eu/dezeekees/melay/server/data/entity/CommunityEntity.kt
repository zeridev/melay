package eu.dezeekees.melay.server.data.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.UUID

object Communities: UUIDTable("communities") {
    val name = varchar("name", 100)
    val description = text("description")
    val iconUrl = varchar("icon", 255).nullable()
    val bannerUrl = varchar("banner", 255).nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
}

class CommunityEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CommunityEntity>(Communities)

    var name by Communities.name
    var description by Communities.description
    var iconUrl by Communities.iconUrl
    var bannerUrl by Communities.bannerUrl
    var createdAt by Communities.createdAt

    val channels by ChannelEntity referrersOn Channels.communityId
}