plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ktor) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false
	
	// this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ktlint) apply false

    alias(libs.plugins.sonarqube)
    alias(libs.plugins.jacoco)
}

subprojects {
    apply {
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
    }

    plugins.withId("org.jetbrains.kotlin.jvm") {
        apply(plugin = "jacoco")
    }
}

sonar {
    properties {
        property("sonar.projectKey", "melay_melay")
        property("sonar.organization", "melay")
    }
}

group = "eu.dezeekees"
version = "0.0.1-SNAPSHOT"
description = "An open source decentralized messaging platform"
