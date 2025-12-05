import io.ktor.plugin.OpenApiPreview

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

ktor {
    @OptIn(OpenApiPreview::class)
    openApi {
        title = "Melay Api"
        version = "0.0.1"
    }
}

dependencies {
    implementation(project(":common"))

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.request.validation)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.rsocket)
    implementation(libs.logback.classic)

    implementation(libs.kotlinx.serialization.protobuf)

    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    implementation(libs.argon2.jvm)

    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.exposed.dao)
    implementation(libs.flyway.core)
    implementation(libs.flyway.database.postgresql)
    implementation(libs.postgresql)

    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.koin.test)
    testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
    jvmToolchain(21)
}


