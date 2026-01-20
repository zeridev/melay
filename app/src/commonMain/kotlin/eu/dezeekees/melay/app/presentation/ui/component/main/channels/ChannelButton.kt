package eu.dezeekees.melay.app.presentation.ui.component.main.channels

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import java.util.UUID

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChannelButton(
    channelName: String,
    channelId: UUID,
    isActive: Boolean,
    onClick: () -> Unit,
    onDelete: (id: UUID) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val backgroundColor by animateColorAsState(
        targetValue = when {
            isActive -> MaterialTheme.colorScheme.primary
            isHovered -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            else -> Color.Transparent
        }
    )

    Surface(
        color = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .clip(CircleShape)
            .clickable(interactionSource = interactionSource, indication = LocalIndication.current, onClick = onClick)
            .hoverable(interactionSource = interactionSource) // Modern hover detection
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "# $channelName",
                style = MaterialTheme.typography.labelMedium,
                color = if (isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
            )

            // Delete icon on hover
            if (isHovered) {
                IconButton(
                    onClick = { onDelete(channelId) },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(20.dp) // adjust size
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete channel",
                        tint = MelayTheme.colorScheme.error
                    )
                }
            }
        }
    }
}