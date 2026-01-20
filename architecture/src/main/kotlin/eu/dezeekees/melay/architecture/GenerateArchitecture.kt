package eu.dezeekees.melay.architecture

import com.structurizr.Workspace
import com.structurizr.util.WorkspaceUtils
import java.io.File

fun main() {
    val workspace = Workspace(
        "Melay",
        "Architecture model for a Kotlin Multiplatform client interacting with a RSocket/REST API backend."
    )
    val model = workspace.model

    // --- People & Systems ---
    val endUser = model.addPerson("End user", "Sends and receives messages on the platform.")
    val messagingPlatform = model.addSoftwareSystem(
        "Messaging Platform",
        "The complete application providing real-time and persistent messaging capabilities."
    )
    endUser.uses(messagingPlatform, "Sends and receives messages using")

    // --- Containers ---
    val clientApp = messagingPlatform.addContainer(
        "Client",
        "A client app in Kotlin Multiplatform",
        "Kotlin Multiplatform (Android, Jvm/Desktop)"
    )
    val backend = messagingPlatform.addContainer(
        "Backend",
        "Provides the messaging service via RSocket and a REST API.",
        "Ktor/Rsocket Api"
    )
    val postgres = messagingPlatform.addContainer(
        "Postgres Database",
        "Stores user information, communities, and messages.",
        "PostgreSQL"
    )
    val staticStore = messagingPlatform.addContainer(
        "Static Store",
        "Stores attached files, profile pictures, and other static files.",
        "Local Disk Directory"
    )

    // --- Explicit Container Relationships (C2 Layer) ---
    // Adding these explicitly ensures they appear in the Container View
    endUser.uses(clientApp, "Uses", "Kotlin UI")
    clientApp.uses(backend, "Makes API calls and opens socket streams", "REST/RSocket")
    backend.uses(postgres, "Reads from and writes to", "JDBC/SQL")
    backend.uses(staticStore, "Stores and retrieves media", "Internal API")

    // --- Backend Components (Structural Approach - C3 Layer) ---
    val authModule =
        backend.addComponent("Identity & Auth", "Handles user registration, login, and JWT security.", "Ktor / JWT")
    val communityModule = backend.addComponent(
        "Community & Channel Service",
        "Manages communities, channels, and memberships.",
        "Ktor / Business Logic"
    )
    val messagingModule =
        backend.addComponent("Real-time Messaging", "Handles RSocket streams and message delivery.", "RSocket / Ktor")
    val dataAccess = backend.addComponent(
        "Data Access Layer",
        "Abstracts database operations using Repositories and DAOs.",
        "Exposed / JDBC"
    )

    // --- Component-Level Relationships ---
    // Client to specific Modules
    clientApp.uses(authModule, "Authenticates with", "REST/JSON")
    clientApp.uses(communityModule, "Manages data via", "REST/JSON")
    clientApp.uses(messagingModule, "Streams messages via", "RSocket")

    // Internal flows
    authModule.uses(dataAccess, "Reads/Writes user data")
    communityModule.uses(dataAccess, "Reads/Writes community data")
    messagingModule.uses(dataAccess, "Persists messages")

    // Components to External Containers
    dataAccess.uses(postgres, "Reads and writes from", "JDBC/SQL")
    communityModule.uses(staticStore, "Stores profile/community images", "Internal API")

    // --- Views ---
    val views = workspace.views

    // C1: Context
    val contextView = views.createSystemContextView(messagingPlatform, "Context", "System Context Diagram")
    contextView.addAllElements()
    contextView.enableAutomaticLayout()

    // C2: Containers
    val containerView = views.createContainerView(messagingPlatform, "Containers", "High-level building blocks.")
    containerView.addAllElements() // This will now include the explicit backend -> postgres/staticStore lines
    containerView.enableAutomaticLayout()

    // C3: Components
    val componentView = views.createComponentView(backend, "BackendOverview", "Structural overview of the Backend.")
    componentView.add(clientApp)
    componentView.add(postgres)
    componentView.add(staticStore)
    componentView.addAllComponents()
    componentView.enableAutomaticLayout()

    componentView.enableAutomaticLayout()

    // --- Add detailed RSocket components (non-invasive: added AFTER the main component view) ---
    // Client-side components
    val httpClientProvider = clientApp.addComponent(
        "HttpClientProvider",
        "Provides Ktor HttpClient with WebSockets & RSocket support",
        "Kotlin"
    )
    val rSocketClient = clientApp.addComponent(
        "RSocketClient",
        "Wraps an RSocket connection and manages requestChannel streams",
        "Kotlin"
    )
    val messageServiceClient = clientApp.addComponent(
        "MessageService (client)",
        "Initiates requestChannel calls and decodes payloads",
        "Kotlin"
    )

    val routingRSocket = backend.addComponent(
        "RSocket Routing",
        "Registers rSocket endpoints and delegates to handlers",
        "Ktor / rSocket"
    )
    val payloadRouteMatcher = backend.addComponent(
        "PayloadRouteMatcher",
        "Parses RoutingMetadata from Payload and extracts route params",
        "Kotlin"
    )
    val messageRSocketHandler = backend.addComponent(
        "MessageRSocketHandler",
        "Handles incoming requestChannel streams and maps Messages to Payloads",
        "Kotlin"
    )
    val messageServiceServer = backend.addComponent(
        "MessageService (server)",
        "Provides stream(channelId) returning a Flow of messages",
        "Kotlin / Service"
    )
    val messageBroadcaster = backend.addComponent(
        "MessageBroadcaster",
        "Publishes message events to SharedFlow streams per channel",
        "Kotlin / Flow"
    )

    // Relationships between those newly added components
    messageServiceClient.uses(rSocketClient, "Uses to open and manage RSocket connection")
    rSocketClient.uses(httpClientProvider, "Gets configured HttpClient")

    // --- Additional related components found in the project ---
    // Client-side helpers
    val tokenService = clientApp.addComponent(
        "TokenService",
        "Provides stored auth token and token lifecycle methods",
        "Kotlin / Service"
    )
    val messageClient = clientApp.addComponent(
        "MessageClient",
        "HTTP client implementation of MessageRepository (non-streaming operations)",
        "Kotlin / HTTP Client"
    )

    // Server-side persistence & mapping
    val messageRepository = backend.addComponent(
        "MessageRepository",
        "Repository abstraction for message CRUD operations",
        "Kotlin / Repository"
    )
    val messageDao = backend.addComponent(
        "MessageDao",
        "Database DAO implementing MessageRepository using Exposed",
        "Kotlin / DAO"
    )
    val apiMessageMapper = backend.addComponent(
        "API MessageMapper",
        "Maps domain models to API responses and back",
        "Kotlin / Mapper"
    )
    val dataMessageMapper = backend.addComponent(
        "Data MessageMapper",
        "Maps database entities to domain models",
        "Kotlin / Mapper"
    )

    // Server wiring & routes
    val configRSocket = backend.addComponent(
        "ConfigRSocket",
        "Installs WebSockets & RSocketSupport and configures rSocket routes",
        "Kotlin / Ktor Config"
    )
    val rSocketRoutes = backend.addComponent(
        "RSocketRoutes",
        "Defines rSocket routing and requestChannel handlers",
        "Kotlin / Routing"
    )
    val messageRoutes = backend.addComponent(
        "MessageRoutes",
        "HTTP routes for message CRUD (creates messages over REST)",
        "Ktor / Routing"
    )

    routingRSocket.uses(payloadRouteMatcher, "Parses incoming payload route")
    routingRSocket.uses(messageRSocketHandler, "Delegates to")
    messageRSocketHandler.uses(messageServiceServer, "Subscribes to")
    messageServiceServer.uses(messageBroadcaster, "Receives published messages from")
    messageBroadcaster.uses(messageServiceServer, "Publishes to")

    // Link new components together
    httpClientProvider.uses(tokenService, "Loads/supplies auth token")
    messageServiceClient.uses(messageClient, "Uses for REST operations if needed")

    messageServiceServer.uses(messageRepository, "Reads/writes messages via")
    messageRepository.uses(messageDao, "Implements using")
    messageDao.uses(dataMessageMapper, "Uses to map DB entities")
    messageRSocketHandler.uses(apiMessageMapper, "Maps domain messages to API responses")

    configRSocket.uses(rSocketRoutes, "Registers rSocket routes")
    rSocketRoutes.uses(routingRSocket, "Delegates to routing internals")
    messageRoutes.uses(messageServiceServer, "Creates messages via")
    messageClient.uses(messageRoutes, "Calls create/update messages via HTTP")

    // When a message is created over HTTP, the server broadcasts it to active RSocket requestChannel streams
    messageServiceServer.uses(messageBroadcaster, "Broadcasts newly created messages to")
    messageBroadcaster.uses(routingRSocket, "Publishes updates to active requestChannel streams")

    // Cross-container connection (client -> backend routing)
    messageServiceClient.uses(routingRSocket, "Calls requestChannel on", "RSocket Payload route")

    // C4: Detailed RSocket component view (this view only; does not alter other views)
    val rsocketDetailFull = views.createComponentView(backend, "RSocketDetail", "Detailed RSocket component view (client + server pieces)")
    rsocketDetailFull.add(httpClientProvider)
    rsocketDetailFull.add(rSocketClient)
    rsocketDetailFull.add(messageServiceClient)
    rsocketDetailFull.add(routingRSocket)
    rsocketDetailFull.add(payloadRouteMatcher)
    rsocketDetailFull.add(messageRSocketHandler)
    rsocketDetailFull.add(messageServiceServer)
    rsocketDetailFull.add(messageRepository)
    rsocketDetailFull.add(messageDao)
    rsocketDetailFull.add(apiMessageMapper)
    rsocketDetailFull.add(dataMessageMapper)
    rsocketDetailFull.add(configRSocket)
    rsocketDetailFull.add(rSocketRoutes)
    rsocketDetailFull.add(messageClient)
    rsocketDetailFull.add(tokenService)
    rsocketDetailFull.add(messageRoutes)
    rsocketDetailFull.add(messageBroadcaster)
    rsocketDetailFull.enableAutomaticLayout()

    WorkspaceUtils.saveWorkspaceToJson(workspace, File("melay2.json"))
}