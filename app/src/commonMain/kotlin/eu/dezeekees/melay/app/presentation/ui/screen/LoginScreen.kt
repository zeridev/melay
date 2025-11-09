package eu.dezeekees.melay.app.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import eu.dezeekees.melay.app.presentation.ui.ScreenType
import eu.dezeekees.melay.app.presentation.ui.component.login.LoginForm
import eu.dezeekees.melay.app.presentation.ui.component.login.LoginLogo
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import eu.dezeekees.melay.app.presentation.viewmodel.LoginViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginScreenContent(
        uiState = uiState,
        onDomainChanged = viewModel::onDomainChanged,
        onUsernameChanged = viewModel::onUsernameChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginViewModel.UiState,
    onDomainChanged: (String) -> Unit,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
) {
    val screenType = ScreenType.fromWindowSizeClass(currentWindowAdaptiveInfo().windowSizeClass)

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
            .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(if(screenType == ScreenType.MOBILE) 0.5f else 1f)
                    .fillMaxWidth()
                    .background(MelayTheme.gradientBrush.primary)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (screenType) {
                    ScreenType.MOBILE -> MobileLoginScreen(
                        uiState,
                        onDomainChanged,
                        onUsernameChanged,
                        onPasswordChanged,
                    )

                    else -> DesktopLoginScreen(
                        uiState,
                        onDomainChanged,
                        onUsernameChanged,
                        onPasswordChanged,
                    )
                }
            }
        }
    }
}

@Composable
private fun MobileLoginScreen(
    uiState: LoginViewModel.UiState,
    onDomainChanged: (String) -> Unit,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        LoginLogo(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            color = MelayTheme.colorScheme.onPrimary,
        )

        LoginForm(
            modifier = Modifier
                .weight(3f)
                .clip(MelayTheme.shapes.extraLarge.copy(
                    bottomEnd = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp)
                ))
                .background(MelayTheme.colorScheme.background),
            textStyle = MelayTheme.typography.headlineMedium,
            domainText = uiState.domain,
            onDomainChange = onDomainChanged,
            usernameText = uiState.username,
            onUsernameChange = onUsernameChanged,
            passwordText = uiState.password,
            onPasswordChange = onPasswordChanged,
            onLoginClick = {}
        )
    }
}

@Composable
private fun DesktopLoginScreen(
    uiState: LoginViewModel.UiState,
    onDomainChanged: (String) -> Unit,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .sizeIn(
                    maxWidth = 800.dp,  // max width of the row
                    maxHeight = 500.dp
                )
                .clip(MelayTheme.shapes.extraLarge)
                .background(MelayTheme.colorScheme.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            LoginForm(
                modifier = Modifier
                    .weight(1f),
                textStyle = MelayTheme.typography.headlineMedium,
                domainText = uiState.domain,
                onDomainChange = onDomainChanged,
                usernameText = uiState.username,
                onUsernameChange = onUsernameChanged,
                passwordText = uiState.password,
                onPasswordChange = onPasswordChanged,
                onLoginClick = {}
            )

            LoginLogo(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                color = MelayTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreenContent(
        uiState = LoginViewModel.UiState(
            domain = "",
            username = "",
            password = "",
        ),
        onDomainChanged = {},
        onUsernameChanged = {},
        onPasswordChanged = {},
    )
}