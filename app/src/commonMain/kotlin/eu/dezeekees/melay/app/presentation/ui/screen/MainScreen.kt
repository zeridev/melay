package eu.dezeekees.melay.app.presentation.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import eu.dezeekees.melay.app.network.HttpClientProvider
import eu.dezeekees.melay.app.presentation.ui.component.main.channels.ChannelTopBar
import eu.dezeekees.melay.app.presentation.ui.component.main.channels.ChannelsRow
import eu.dezeekees.melay.app.presentation.ui.component.main.channels.CreateChannelPopup
import eu.dezeekees.melay.app.presentation.ui.component.main.chat.ChatSection
import eu.dezeekees.melay.app.presentation.ui.component.main.chat.OnlineUser
import eu.dezeekees.melay.app.presentation.ui.component.main.communities.CommunityBar
import eu.dezeekees.melay.app.presentation.ui.component.main.communities.CreateCommunityPopup
import eu.dezeekees.melay.app.presentation.ui.component.main.communities.DiscoverCommunityPopup
import eu.dezeekees.melay.app.presentation.ui.component.main.communities.UpdateCommunityPopup
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import eu.dezeekees.melay.app.presentation.viewmodel.MainScreenViewmodel
import eu.dezeekees.melay.app.presentation.viewmodel.main.CreateChannelPopupViewModel
import eu.dezeekees.melay.app.presentation.viewmodel.main.CreateCommunityPopupViewModel
import eu.dezeekees.melay.app.presentation.viewmodel.main.DiscoverCommunityPopupViewModel
import eu.dezeekees.melay.app.presentation.viewmodel.main.UpdateCommunityPopupViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    viewmodel: MainScreenViewmodel = koinViewModel(),
    createChannelPopupViewModel: CreateChannelPopupViewModel = koinViewModel(),
    createCommunityPopupViewModel: CreateCommunityPopupViewModel = koinViewModel(),
    updateCommunityPopupViewModel: UpdateCommunityPopupViewModel = koinViewModel(),
    discoverCommunityPopupViewModel: DiscoverCommunityPopupViewModel = koinViewModel(),
) {
    val isLoading by viewmodel.isLoading.collectAsStateWithLifecycle()
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val httpClientProvider = koinInject<HttpClientProvider>()

    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory(httpClientProvider.get()))
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
            CommunityBar(
                uiState = uiState,
                onCommunityBarItemClick = { communityId -> viewmodel.setSelectedCommunity(communityId) },
                onCreateCommunityClick = { createCommunityPopupViewModel.open() },
                onDiscoverCommunityClick = { discoverCommunityPopupViewModel.open(uiState.userCommunities) },
            )

            CreateCommunityPopup(
                createCommunityPopupViewModel,
                onCreateSuccess = {}
            )

            UpdateCommunityPopup(
                updateCommunityPopupViewModel,
                onUpdateSuccess = { viewmodel.reloadAllCommunities() }
            )

            DiscoverCommunityPopup(
                discoverCommunityPopupViewModel,
                onSuccessfulJoin = { viewmodel.reloadAllCommunities() }
            )

            // main screen
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MelayTheme.shapes.medium)
                    .background(MelayTheme.colorScheme.surface)
            ) {

                // left row (channels & logged in user)
                ChannelsRow(
                    viewmodel.selectedCommunity,
                    onCreateChannelClick = { createChannelPopupViewModel.open() },
                    onLeaveCommunityClick = { viewmodel.leaveSelectedCommunity() },
                    onDeleteChannelClick = { channelId -> createChannelPopupViewModel.delete(
                        channelId,
                        onSuccess = { viewmodel.reloadAllCommunities() }
                    ) },
                    onUpdateCommunityClick = { updateCommunityPopupViewModel.open(viewmodel.selectedCommunity) },
                    onDeleteCommunityClick = { viewmodel.deleteSelectedCommunity() }
                )

                CreateChannelPopup(
                    viewmodel.selectedCommunity,
                    createChannelPopupViewModel,
                    onCreateSuccess = { viewmodel.reloadAllCommunities() },
                )

                Column(
                    modifier = Modifier
                ) {

                    // channel bar
                    ChannelTopBar(
                        uiState = uiState,
                        onMemberListToggleClick = { viewmodel.toggleUsersRowOpen() }
                    )

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
                            ChatSection()
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