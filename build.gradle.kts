plugins {
    kotlin("jvm") version "1.6.0"
    application
    id("com.ncorti.ktfmt.gradle") version "0.11.0"
}

ktfmt {
    kotlinLangStyle()
}

group = "org.bundleproject.api"
version = "0.1.2"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    val ktorVersion: String by project
    val kotlinVersion: String by project
    val logbackVersion: String by project

    implementation("guru.zoroark:ktor-rate-limit:0.0.2")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("io.ktor:ktor-client-gson:$ktorVersion")
    implementation("io.ktor:ktor-client-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.bundleproject:libversion:0.0.2")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
}

application {
    mainClass.set("org.bundleproject.api.ApplicationKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

tasks.findByPath(":test")?.enabled = false
