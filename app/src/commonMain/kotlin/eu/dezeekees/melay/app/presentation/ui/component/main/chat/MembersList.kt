package eu.dezeekees.melay.app.presentation.ui.component.main.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun OnlineUser() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .clickable(onClick = {})
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        AsyncImage(
            model = "https://cdn.discordapp.com/avatars/361429063245758467/c71bc9f1232edcfd77afa701336b4c3c.webp?size=128",
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Username",
                style = MaterialTheme.typography.labelMedium,
            )

            Text(
                text = "status text",
                style = MaterialTheme.typography.labelSmall,
            )
        }

    }
}