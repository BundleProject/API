package org.bundleproject

import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.bundleproject.plugins.configureRateLimiting
import org.bundleproject.plugins.configureRouting
import org.bundleproject.plugins.configureSerialization
import org.bundleproject.plugins.configureStatusPages
import org.bundleproject.utils.port

fun Application.configurePlugins() {
    configureRateLimiting()
    configureStatusPages()
    configureSerialization()
    configureRouting()
}

fun main() {
    embeddedServer(Netty, port = port, host = "0.0.0.0") { configurePlugins() }.start(wait = true)
}
