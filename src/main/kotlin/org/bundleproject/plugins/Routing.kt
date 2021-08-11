package org.bundleproject.plugins

import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.response.*
import org.bundleproject.utils.fetchAssets
import org.bundleproject.utils.resolveUrl

fun Application.configureRouting() {
    routing {
        get("/") {
            TODO("Send docs here")
        }
        get("/v1/mods/{id}/{platform}/{minecraftVersion}/{version}") {
            val assets = fetchAssets()
            val id = call.parameters["id"]!!
            val platform = call.parameters["platform"]!!
            val minecraftVersion = call.parameters["minecraftVersion"]!!
            val version = call.parameters["version"]!!
            val mod = assets.mods[id]
            val modData = mod
                ?.platforms
                ?.get(platform)
                ?.get(minecraftVersion)
                ?.get(version)
                ?: return@get call.respond(
                    mapOf(
                        "success" to false,
                        "error" to "Invalid mod"
                    )
                )
            call.respond(
                mapOf(
                    "success" to true,
                    "data" to mapOf(
                        "url" to resolveUrl(modData),
                        "metadata" to mod.metadata
                    )
                )
            )
        }
    }
}
