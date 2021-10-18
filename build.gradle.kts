plugins {
    kotlin("jvm") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("com.ncorti.ktfmt.gradle") version "0.7.0"
}

ktfmt {
    kotlinLangStyle()
}

group = "org.bundleproject"
version = "0.0.1"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    val ktor_version: String by project
    val kotlin_version: String by project
    val logback_version: String by project

    implementation("guru.zoroark:ktor-rate-limit:0.0.2")
    implementation("com.github.zafarkhaja:java-semver:0.9.0")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-apache:$ktor_version")
    implementation("io.ktor:ktor-gson:$ktor_version")
    implementation("io.ktor:ktor-client-gson:$ktor_version")
    implementation("io.ktor:ktor-client-auth:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
}

tasks {
    jar {
        manifest {
            attributes(
                "Main-Class" to "org.bundleproject.ApplicationKt"
            )
        }
    }

    shadowJar {
        archiveClassifier.set("")
    }

    compileKotlin {
        kotlinOptions {
            freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}
