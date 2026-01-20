package eu.dezeekees.melay.app.presentation.ui.component.main.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.dezeekees.melay.app.logic.model.message.MessageResponse
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import eu.dezeekees.melay.app.presentation.viewmodel.MainScreenViewmodel
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ChatSection(
    uiState: MainScreenViewmodel.UIState,
    onSendMessage: () -> Unit,
    onChatInputChanged: (TextFieldValue) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        val listState = rememberLazyListState()

        LaunchedEffect(uiState.channelMessages.size) {
            // Scroll to the bottom (which is index 0 in reverseLayout)
            listState.animateScrollToItem(0)
        }

        LazyColumn(
            modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
            state = listState,
            reverseLayout = true,
            verticalArrangement = Arrangement.Bottom,
        ) {
            items(uiState.channelMessages, key = { it.id }) { message ->
                MessageRow(message)
            }
        }

        ChatInput(
            onSendMessage = { onSendMessage() },
            uiState = uiState,
            onChatInputChanged = { onChatInputChanged(it) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageRow(message: MessageResponse) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Avatar circle with username initial
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = MelayTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(18.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message.author.username.firstOrNull()?.uppercase() ?: "?",
                color = MelayTheme.colorScheme.onSecondary
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Message content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Username + timestamp
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TooltipBox(
                    positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Right),
                    tooltip = { PlainTooltip {
                        Text(message.author.username)
                    } },
                    state = rememberTooltipState(
                        isPersistent = true,
                    )
                ) {
                    Text(
                        text = message.author.displayName,
                        color = MelayTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                val localTime = message.createdAt.toLocalDateTime(TimeZone.currentSystemDefault())
                Text(
                    text = "${localTime.hour}:${localTime.minute}", // HH:mm
                    color = MelayTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Message content text
            Text(
                text = message.content,
                color = MelayTheme.colorScheme.onSurface,
                fontSize = 16.sp
            )
        }
    }
}