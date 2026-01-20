package eu.dezeekees.melay.app.presentation.ui.component.main.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import eu.dezeekees.melay.app.presentation.viewmodel.MainScreenViewmodel

@Composable
fun ChatInput(
    modifier: Modifier = Modifier,
    onSendMessage: () -> Unit,
    uiState: MainScreenViewmodel.UIState,
    onChatInputChanged: (TextFieldValue) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = MelayTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 6.dp, vertical = 4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(32.dp)
                    .focusRequester(focusRequester),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MelayTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Text field
            BasicTextField(
                value = uiState.chatInputText,
                onValueChange = { onChatInputChanged(it) },
                textStyle = TextStyle(color = MelayTheme.colorScheme.onSurfaceVariant, fontSize = 16.sp),
                cursorBrush = SolidColor(MelayTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 0.dp, vertical = 4.dp)
                    .onPreviewKeyEvent { keyEvent ->
                        if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Enter) {
                            if (keyEvent.isShiftPressed) {
                                val newText = uiState.chatInputText.text + "\n"
                                onChatInputChanged(TextFieldValue(
                                    text = newText,
                                    selection = TextRange(newText.length) // cursor at end
                                ))
                            } else {
                                // Send message
                                onSendMessage()
                            }
                            true
                        } else {
                            false
                        }
                    }
                    .focusRequester(focusRequester)
            )

            IconButton(
                onClick = onSendMessage,
                modifier = Modifier
                    .size(32.dp)
                    .focusRequester(focusRequester),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = MelayTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
