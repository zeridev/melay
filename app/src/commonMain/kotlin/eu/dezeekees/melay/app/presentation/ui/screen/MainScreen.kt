package eu.dezeekees.melay.app.presentation.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import eu.dezeekees.melay.app.presentation.ui.component.main.ChannelButton
import eu.dezeekees.melay.app.presentation.ui.component.main.ChannelTopBar
import eu.dezeekees.melay.app.presentation.ui.component.main.ChannelsRow
import eu.dezeekees.melay.app.presentation.ui.component.main.CommunityBar
import eu.dezeekees.melay.app.presentation.ui.component.main.OnlineUser
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import eu.dezeekees.melay.app.presentation.viewmodel.MainScreenViewmodel
import io.ktor.client.HttpClient
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    viewmodel: MainScreenViewmodel = koinViewModel()
) {
    val isLoading by viewmodel.isLoading.collectAsStateWithLifecycle()
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val httpClient = koinInject<HttpClient>()

    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory(httpClient))
            }
            .build()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MelayTheme.colorScheme.surfaceDim)
                .fillMaxSize()
        ) {
            CommunityBar()

            // main screen
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MelayTheme.shapes.medium)
                    .background(MelayTheme.colorScheme.surface)
            ) {

                // left row (channels & logged in user)
                ChannelsRow()

                Column(
                    modifier = Modifier
                ) {

                    // channel bar
                    ChannelTopBar(viewmodel)

                    // channel content
                    Row(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        // middle row (chat)
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {

                        }

                        // right row (community users)
                        AnimatedVisibility(
                            visible = uiState.usersRowOpen,
                            enter = slideInHorizontally { it },
                            exit = slideOutHorizontally { it },
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(200.dp)
                                    .fillMaxHeight()
                                    .padding(4.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                OnlineUser()
                                OnlineUser()
                            }
                        }
                    }

                }
            }
        }
    }
}