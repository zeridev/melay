package eu.dezeekees.melay.server.data.dao

import eu.dezeekees.melay.server.data.entity.ChannelEntity
import eu.dezeekees.melay.server.data.entity.Channels
import eu.dezeekees.melay.server.data.entity.CommunityEntity
import eu.dezeekees.melay.server.data.mapper.ChannelMapper
import eu.dezeekees.melay.server.logic.exception.NotFoundException
import eu.dezeekees.melay.server.logic.model.Channel
import eu.dezeekees.melay.server.logic.repository.ChannelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class ChannelDao: ChannelRepository {
    override suspend fun create(channel: Channel): Channel = withContext(Dispatchers.IO) {
        transaction {
            val communityEntity = CommunityEntity.findById(channel.communityId!!) ?:
                throw NotFoundException("Community ID ${channel.communityId} not found")

            val entity = ChannelEntity.new {
                name = channel.name
                type = channel.type
                position = channel.position
                communityId = communityEntity.id
            }

            entity.flush()
            entity.refresh()

            ChannelMapper.toChannel(entity)
        }
    }

    override suspend fun update(channel: Channel): Channel = withContext(Dispatchers.IO) {
        val entity = ChannelEntity.findByIdAndUpdate(channel.id!!) { newChannel ->
            newChannel.name = channel.name.takeIf { it.isNotBlank() } ?: newChannel.name
            newChannel.position = channel.position
            newChannel.type = channel.type
        }

        if(entity == null) {
            throw NotFoundException("Channel with id ${channel.id} not found")
        }

        entity.flush()
        entity.refresh()

        ChannelMapper.toChannel(entity)
    }

    override suspend fun delete(channelId: UUID) = withContext(Dispatchers.IO) {
        transaction {
            Channels.deleteWhere { id eq channelId }

            return@transaction
        }
    }
}