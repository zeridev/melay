plugins {
    kotlin("jvm") version "2.2.10" apply false
    kotlin("plugin.spring") version "2.2.10" apply false
    id("org.springframework.boot") version "4.0.0-M3" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
	
	// this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}

group = "eu.dezeekees"
version = "0.0.1-SNAPSHOT"
description = "An open source decentralized messaging platform"
