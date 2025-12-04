package eu.dezeekees.melay.architecture

import com.structurizr.Workspace
import com.structurizr.component.ComponentFinderBuilder
import com.structurizr.component.ComponentFinderStrategyBuilder
import com.structurizr.component.filter.ExcludeFullyQualifiedNameRegexFilter
import com.structurizr.component.filter.IncludeFullyQualifiedNameRegexFilter
import com.structurizr.component.matcher.NameSuffixTypeMatcher
import com.structurizr.component.matcher.RegexTypeMatcher
import com.structurizr.component.description.FirstSentenceDescriptionStrategy
import com.structurizr.component.description.DefaultDescriptionStrategy
import com.structurizr.util.WorkspaceUtils
import java.io.File

fun main() {
    val workspace = Workspace("Melay", "Architecture model for a Kotlin Multiplatform client interacting with a RSocket/REST API backend.")
    val model = workspace.model

    val endUser = model.addPerson("End user", "Sends and receives messages on the platform.")

    val messagingPlatform = model.addSoftwareSystem("Messaging Platform", "The complete application providing real-time and persistent messaging capabilities.")

    // Relationship (C1)
    endUser.uses(messagingPlatform, "Sends and receives messages using")

    // Containers inside the Messaging Platform
    val clientApp = messagingPlatform.addContainer("Client", "A client app in Kotlin Multiplatform", "Kotlin Multiplatform (Android, Jvm/Desktop)")
    val backend = messagingPlatform.addContainer("Backend", "Provides the messaging service via RSocket and a REST API.", "Ktor/Rsocket Api")
    val postgres = messagingPlatform.addContainer("Postgres Database", "Stores user information, communities, and messages.", "PostgreSQL")
    val staticStore = messagingPlatform.addContainer("Static Store", "Stores attached files, profile pictures, and other static files.", "Local Disk Directory")

    // Relationships (C2) - based on your diagram
    endUser.uses(clientApp, "Uses", "Kotlin UI/OS Networking")

    clientApp.uses(backend, "Makes API calls and socket connections to", "REST/RSocket")

    backend.uses(postgres, "Reads and writes from", "JDBC/SQL")
    backend.uses(staticStore, "Writes to and deletes from", "HTTP/Internal API")

    val rootPackageRegex = "^eu\\.dezeekees\\.melay\\.server\\..*"
    val excludeConfigRoutesRegex = ".*ConfigRoutes.*"

    ComponentFinderBuilder()
        .forContainer(backend)
        .fromClasses("server/build/libs/server-all.jar")
        .filteredBy(IncludeFullyQualifiedNameRegexFilter(rootPackageRegex))

        .withStrategy(
            ComponentFinderStrategyBuilder()
                .matchedBy(RegexTypeMatcher(".*RoutesKt"))
                .withName(RouteNamingStrategy())
                .filteredBy(ExcludeFullyQualifiedNameRegexFilter(excludeConfigRoutesRegex))
                .withTechnology("Ktor Routes/Controller")
                .build()
        )
        // 2. Match Services (Business Logic Layer)
        .withStrategy(
            ComponentFinderStrategyBuilder()
                .matchedBy(NameSuffixTypeMatcher("Service"))
                .withTechnology("Business Logic Service")
                .build()
        )
        // 3. Match DAOs/Repositories (Persistence Layer)
        .withStrategy(
            ComponentFinderStrategyBuilder()
                .matchedBy(NameSuffixTypeMatcher("Dao"))
                .withTechnology("Data Access Object")
                .build()
        )
        // 4. Match Utils (Helper Functions)
        .withStrategy(
            ComponentFinderStrategyBuilder()
                .matchedBy(NameSuffixTypeMatcher("Util"))
                .withTechnology("Utility Function")
                .build()
        )
        .withStrategy(
            ComponentFinderStrategyBuilder()
                .matchedBy(NameSuffixTypeMatcher("Repository"))
                .withTechnology("Repository Interface")
                .build()
        )

        .build()
        .run()

    val userDao = backend.getComponentWithName("User Dao")

    val userRepository = backend.getComponentWithName("User Repository")

    val authService = backend.getComponentWithName("Auth Service")
    val userService = backend.getComponentWithName("User Service")

    val authRoutes = backend.getComponentWithName("Auth Routes")
    val userRoutes = backend.getComponentWithName("User Routes")

    userDao?.let {
        it.uses(postgres, "Reads and writes user data from", "JDBC/SQL")
        it.uses(userRepository, "Implements")
    }

    authService?.let {
        it.uses(userRepository, "Uses repository to read and write data")
        authRoutes?.uses(it, "")
    }

    userService?.let {
        it.uses(userRepository, "Uses repository to write data")
        userRoutes?.uses(it, "")
    }

    authRoutes?.let { clientApp.uses(it, "Makes authentication requests to", "REST/JSON") }
    userRoutes?.let { clientApp.uses(it, "Makes user related requests to", "REST/JSON") }

    val views = workspace.views

    // C1: Context View
    val contextView = views.createSystemContextView(messagingPlatform, "Context")
    contextView.addAllElements()
    contextView.enableAutomaticLayout()

    // C2: Container View
    val containerView = views.createContainerView(messagingPlatform, "Containers", "The decomposition of the Messaging Platform into its high-level building blocks.")
    containerView.addAllElements()
    containerView.enableAutomaticLayout()

    // C3: Component View (Backend)
    val componentView = views.createComponentView(backend, "Backend Components", "The decomposition of the Backend API container.")
    componentView.add(clientApp)
    componentView.add(postgres)
    componentView.add(staticStore)
    componentView.addAllComponents()
    componentView.enableAutomaticLayout()

    WorkspaceUtils.saveWorkspaceToJson(workspace, File("melay.json"))
}