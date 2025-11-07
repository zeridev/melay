package eu.dezeekees.melay.server.data.dao

import eu.dezeekees.melay.server.data.mapper.UserMapper
import eu.dezeekees.melay.server.data.entity.UserEntity
import eu.dezeekees.melay.server.data.entity.Users
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UserDao : UserRepository {
    override suspend fun findById(id: UUID): User? = withContext(Dispatchers.IO) {
        transaction {
            UserEntity.findById(id)?.let(UserMapper::toUser)
        }
    }

    override suspend fun findByUsername(username: String): User? = withContext(Dispatchers.IO) {
        transaction {
            UserEntity.find { Users.username eq username }
                .firstOrNull()
                ?.let(UserMapper::toUser)
        }
    }

    override suspend fun save(user: User): User = withContext(Dispatchers.IO) {
        transaction {
            val entity = UserEntity.new {
                username = user.username
                displayName = user.username
                passwordHash = user.passwordHash
            }

            entity.flush()
            entity.refresh()

            UserMapper.toUser(entity)
        }
    }
}