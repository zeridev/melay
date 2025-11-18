import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlin.plugin.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm()
    
    sourceSets {
        val androidMain by getting {
			kotlin.srcDirs("src/androidMain/kotlin")
			dependencies {
				implementation(compose.preview)
				implementation(libs.androidx.activity.compose)
                implementation(libs.koin.android)
                implementation(libs.koin.androidx.compose)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.slf4j.android)
			}
		}
        val commonMain by getting {
			kotlin.srcDirs("src/commonMain/kotlin")
			dependencies {
                implementation(project(":common"))
				implementation(compose.runtime)
				implementation(compose.foundation)
				implementation(compose.material3)
				implementation(compose.ui)
				implementation(compose.components.resources)
				implementation(compose.components.uiToolingPreview)
				implementation(libs.androidx.lifecycle.viewmodelCompose)
				implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(libs.androidx.lifecycle)
                implementation(libs.androidx.navigation.compose)
                implementation(libs.androidx.datastore)
                implementation(libs.androidx.datastore.prefrences)
                implementation(libs.material3.adaptive)
                implementation(libs.material.icons.extended)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.auth)
                implementation(libs.ktor.serialization.kotlinx.json)

                api(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.koin.compose)
			}
		}
        val commonTest by getting {
			kotlin.srcDirs("src/commonTest/kotlin")
			dependencies {
				implementation(libs.kotlin.test)
			}
		}
        val jvmMain by getting {
			kotlin.srcDirs("src/jvmMain/kotlin")
			dependencies {
				implementation(compose.desktop.currentOs)
				implementation(libs.kotlinx.coroutinesSwing)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.logback.classic)
			}
		}
    }
}

android {
    namespace = "eu.dezeekees.melay.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "eu.dezeekees.melay.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        buildToolsVersion= libs.versions.android.buildToolsVersion.get().toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "eu.dezeekees.melay.app.MainKt"

        buildTypes.release.proguard {
            configurationFiles.from(file("./proguard-desktop.pro"))
            isEnabled.set(true)
            optimize.set(false)
            obfuscate.set(true)
        }

        nativeDistributions {
            targetFormats(TargetFormat.Msi, TargetFormat.Deb)
            packageName = "eu.dezeekees.melay.app"
            packageVersion = "1.0.0"
        }
    }
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

