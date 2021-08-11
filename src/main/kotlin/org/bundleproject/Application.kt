package org.bundleproject

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import org.bundleproject.plugins.*
import org.bundleproject.utils.fetchAssets

fun main() {
    val portProp = System.getProperty("server.port")
    var port = 8080
    if (portProp != null) {
        try {
            port = portProp.toInt()
        } catch (ignored: NumberFormatException) {}
    }
    embeddedServer(Netty, port = port, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
