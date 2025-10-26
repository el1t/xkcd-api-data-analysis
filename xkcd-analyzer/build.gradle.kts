import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
	kotlin("jvm") version "2.2.20"
	kotlin("plugin.serialization") version "2.2.0"
	id("io.kotest") version "6.0.4"
	id("org.jetbrains.kotlin.plugin.power-assert") version "2.2.0"
}

group = "co.tsung.xkcd"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	testImplementation(kotlin("test"))
	// https://github.com/Kotlin/kotlinx.coroutines/releases
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
	// https://github.com/Kotlin/kotlinx.serialization/releases
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

	// https://github.com/ktorio/ktor/releases
	val ktorVersion = "3.3.1"
	implementation("io.ktor:ktor-client-core:$ktorVersion")
	implementation("io.ktor:ktor-client-cio:$ktorVersion")
	// Content negotiation
	implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
	implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

	// https://github.com/qos-ch/logback/releases
	val logbackVersion = "1.5.19"
	implementation("ch.qos.logback:logback-classic:$logbackVersion")

	// Testing
	// https://github.com/kotest/kotest/releases
	val kotestVersion = "6.0.4"
	testImplementation("io.kotest:kotest-framework-engine:$kotestVersion")
	testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
	testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
	functions = listOf("io.kotest.matchers.shouldBe")
}

tasks.test {
	useJUnitPlatform()
}
kotlin {
	jvmToolchain(14)
}
