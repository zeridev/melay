package eu.dezeekees.melay.app.presentation.ui.component.main

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme

@Composable
fun ChannelsRow() {
    Column(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            model = "https://cdn.discordapp.com/banners/523059903812599811/23bc6cc98a0244b76303ff6ba8486d6d.webp?size=1024",
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

        Text(
            text = "Server Name",
            style = MelayTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth()
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
            ChannelButton(
                "test-channel",
                true,
                onClick = {},
            )

            ChannelButton(
                "test-channel-two",
                false,
                onClick = {}
            )
        }
    }
}