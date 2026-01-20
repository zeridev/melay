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

    WorkspaceUtils.saveWorkspaceToJson(workspace, File("melay2.json"))
}