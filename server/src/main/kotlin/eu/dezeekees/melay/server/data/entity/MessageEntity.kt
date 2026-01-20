package eu.dezeekees.melay.server.data.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.UUID

object Messages : UUIDTable("messages") {
    val channelId = reference(
        "channel_id",
        Channels,
        onDelete = ReferenceOption.CASCADE
    )

    val authorId = reference(
        "author_id",   // column name
        Users,         // reference table
        onDelete = ReferenceOption.CASCADE
    )

    val content = text("content")

    val createdAt = timestamp("created_at")
        .defaultExpression(CurrentTimestamp)

    val editedAt = timestamp("edited_at")
        .nullable()
}

class MessageEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MessageEntity>(Messages)

    var channelId by Messages.channelId
    var author by UserEntity referencedOn Messages.authorId
    var content by Messages.content
    var createdAt by Messages.createdAt
    var editedAt by Messages.editedAt
}
