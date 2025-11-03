plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot")
	id("io.spring.dependency-management")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(24)
	}
}

val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
    implementation(project(":common"))

    implementation(libs.springdoc.openapi.webflux.ui)
    implementation(libs.spring.boot.starter.data.r2dbc)
    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.spring.boot.starter.rsocket)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.reactor.kotlin.extensions)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.nimbus.jose.jwt)

    developmentOnly(libs.spring.boot.devtools)

    runtimeOnly(libs.r2dbc.postgresql)
    implementation(libs.flyway.core)
    implementation(libs.flyway.database.postgresql)
    implementation(libs.postgresql)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.reactor.test)
    testImplementation(libs.kotlin.testJunit)
    testImplementation(libs.kotlinx.coroutines.reactor)
    testImplementation(libs.mockito.kotlin)
    testRuntimeOnly(libs.junit.platform.launcher)

    mockitoAgent("org.mockito:mockito-core") { isTransitive = false }
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
}

