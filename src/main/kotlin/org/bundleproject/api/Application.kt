package org.bundleproject.api

import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.bundleproject.api.plugins.configureRateLimiting
import org.bundleproject.api.plugins.configureRouting
import org.bundleproject.api.plugins.configureSerialization
import org.bundleproject.api.plugins.configureStatusPages
import org.bundleproject.api.utils.port

fun Application.configurePlugins() {
    configureRateLimiting()
    configureStatusPages()
    configureSerialization()
    configureRouting()
}

fun main() {
    embeddedServer(Netty, port = port, host = "0.0.0.0") { configurePlugins() }.start(wait = true)
}
