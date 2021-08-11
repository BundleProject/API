package org.bundleproject

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import org.bundleproject.plugins.*
import org.bundleproject.utils.fetchAssets

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT")?.toIntOrNull() ?: 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
