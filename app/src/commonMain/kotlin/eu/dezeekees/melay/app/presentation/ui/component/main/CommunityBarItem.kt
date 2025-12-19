package eu.dezeekees.melay.app.presentation.ui.component.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun CommunityBarItem() {
    AsyncImage(
        model = "https://cdn.discordapp.com/avatars/361429063245758467/c71bc9f1232edcfd77afa701336b4c3c.webp?size=128",
        contentDescription = null,
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}