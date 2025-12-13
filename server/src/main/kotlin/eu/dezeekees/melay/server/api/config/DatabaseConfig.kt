package eu.dezeekees.melay.server.api.config

import eu.dezeekees.melay.server.data.entity.Channels
import eu.dezeekees.melay.server.data.entity.Communities
import eu.dezeekees.melay.server.data.entity.UserCommunityMemberships
import eu.dezeekees.melay.server.data.entity.Users
import io.ktor.server.config.ApplicationConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfig {
    fun init(config: ApplicationConfig) {
        val url = config.property("database.url").getString()
        val driver = config.property("database.driver").getString()
        val username = config.property("database.username").getString()
        val password = config.property("database.password").getString()

        Database.connect(
            url = url,
            driver = driver,
            user = username,
            password = password
        )

        transaction {
            SchemaUtils.create(
                Users,
                Communities,
                Channels,
                UserCommunityMemberships
            )
        }
    }
}