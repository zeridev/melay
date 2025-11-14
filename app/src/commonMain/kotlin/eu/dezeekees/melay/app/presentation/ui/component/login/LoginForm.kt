package eu.dezeekees.melay.app.presentation.ui.component.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    domainText: String,
    onDomainChange: (String) -> Unit,
    usernameText: String,
    usernameErrorText: String,
    onUsernameChange: (String) -> Unit,
    passwordText: String,
    passwordErrorText: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    loginButtonEnabled: Boolean,
) {
    var hidePassword by remember { mutableStateOf(true) }

    Box(
        modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Spacer(Modifier.height(0.dp))
            Text(
                text = "Welcome",
                style = textStyle,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(0.dp),
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = domainText,
                    onValueChange = onDomainChange,
                    label = { Text(text = "Domain") },
                    placeholder = { Text(text = "melay.example.com") },
                    singleLine = true,
                    supportingText = { Text(text = "") },
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = usernameText,
                    onValueChange = onUsernameChange,
                    label = { Text(text = "Username") },
                    placeholder = { Text(text = "Enter your username") },
                    supportingText = { Text(
                        text = usernameErrorText,
                        color = MaterialTheme.colorScheme.error
                    ) },
                    singleLine = true,
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = passwordText,
                    onValueChange = onPasswordChange,
                    label = { Text(text = "Password") },
                    placeholder = { Text(text = "Enter your password") },
                    supportingText = { Text(
                        text = passwordErrorText,
                        color = MaterialTheme.colorScheme.error
                    )},
                    singleLine = true,
                    visualTransformation = if(hidePassword) PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = {
                        val icon = if(!hidePassword)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff

                        val description = if(!hidePassword) "Hide password" else "Show password"
                        IconButton(
                            onClick = { hidePassword = !hidePassword },
                            modifier = Modifier
                                .pointerHoverIcon(PointerIcon.Default)
                        ) {
                            Icon(imageVector = icon, contentDescription = description)
                        }
                    }
                )
            }

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = loginButtonEnabled
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}