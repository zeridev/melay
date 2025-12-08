plugins {
    kotlin("jvm")
}

dependencies {
    testImplementation(kotlin("test"))
    api(libs.kotlinx.serialization.protobuf)
    api(libs.rsocket.core)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}