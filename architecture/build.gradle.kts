plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.structurizr.client)
    implementation(libs.structurizr.component)
}

kotlin {
    jvmToolchain(21)
}