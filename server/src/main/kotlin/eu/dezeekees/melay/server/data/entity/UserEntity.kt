package eu.dezeekees.melay.server.data.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.*

object Users : UUIDTable("user") {
    val username = varchar("username", 32).uniqueIndex()       // VARCHAR(32) UNIQUE NOT NULL
    val displayName = varchar("display_name", 50)  // VARCHAR(50)
    val passwordHash = text("password_hash")                   // TEXT NOT NULL
    val profilePicture = text("profile_picture").default("")   // TEXT
    val profileDescription = text("profile_description").default("")// TEXT
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(Users)

    var username by Users.username
    var displayName by Users.displayName
    var passwordHash by Users.passwordHash
    var profilePicture by Users.profilePicture
    var profileDescription by Users.profileDescription
    var createdAt by Users.createdAt
}
