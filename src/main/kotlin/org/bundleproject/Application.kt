package org.bundleproject

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.bundleproject.plugins.*
import org.bundleproject.utils.port

fun main() {
    embeddedServer(Netty, port = port, host = "0.0.0.0") {
        configureRateLimiting()
        configureStatusPages()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
