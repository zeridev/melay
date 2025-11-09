package eu.dezeekees.melay.app.presentation.ui.component.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    domainText: String,
    onDomainChange: (String) -> Unit,
    usernameText: String,
    onUsernameChange: (String) -> Unit,
    passwordText: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
) {
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
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = domainText,
                    onValueChange = onDomainChange,
                    label = { Text(text = "Domain") },
                    placeholder = { Text(text = "melay.example.com") },
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = usernameText,
                    onValueChange = onUsernameChange,
                    label = { Text(text = "Username") },
                    placeholder = { Text(text = "Enter your username") },
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = passwordText,
                    onValueChange = onPasswordChange,
                    label = { Text(text = "Password") },
                    placeholder = { Text(text = "Enter your password") },
                )
            }

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth(),
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