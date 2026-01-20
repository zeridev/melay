plugins {
    kotlin("jvm")
}

dependencies {
    testImplementation(kotlin("test"))
    api(libs.kotlinx.serialization.protobuf)
    api(libs.rsocket.core)
    api(libs.exposed.kotlin.datetime)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}