package eu.dezeekees.melay.app.presentation.ui.component.main.channels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.presentation.ui.component.main.communities.ServerNameDropdown
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import eu.dezeekees.melay.app.presentation.viewmodel.MainScreenViewmodel
import java.util.UUID

@Composable
fun ChannelsRow(
    selectedCommunity: CommunityResponse,
    onCreateChannelClick: () -> Unit,
    onLeaveCommunityClick: () -> Unit,
    onDeleteChannelClick: (id: UUID) -> Unit,
) {
    Column(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if(selectedCommunity.bannerUrl.isNotBlank()) {
            AsyncImage(
                model = selectedCommunity.bannerUrl,
                contentDescription = "server banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .clip(RoundedCornerShape(
                        topStart = CornerSize(0.dp),
                        topEnd = CornerSize(0.dp),
                        bottomEnd = MelayTheme.shapes.medium.bottomEnd,
                        bottomStart = MelayTheme.shapes.medium.bottomStart
                    )),
                contentScale = ContentScale.Crop,
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .clip(RoundedCornerShape(
                        topStart = CornerSize(0.dp),
                        topEnd = CornerSize(0.dp),
                        bottomEnd = MelayTheme.shapes.medium.bottomEnd,
                        bottomStart = MelayTheme.shapes.medium.bottomStart
                    ))
                    .background(MelayTheme.colorScheme.primaryContainer)
            )
        }

        ServerNameDropdown(
            selectedCommunity,
            onCreateChannelClick,
            onLeaveCommunityClick,
        )

        Box(
            modifier = Modifier
                .height(1.dp)
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth()
        ) {
            Spacer(
                Modifier
                    .clip(CircleShape)
                    .background(MelayTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                    .fillMaxSize()
            )
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            selectedCommunity.channels.forEach { channel ->
                ChannelButton(
                    channelName = channel.name,
                    channelId = channel.id,
                    isActive = false,
                    onClick = {},
                    onDelete = { channelId -> onDeleteChannelClick(channelId) },
                )
            }
        }
    }
}