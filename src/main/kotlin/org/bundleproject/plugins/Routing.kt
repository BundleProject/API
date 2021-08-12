package org.bundleproject.plugins

import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import org.bundleproject.json.ModData
import org.bundleproject.utils.ModNotFoundException
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
                    HttpStatusCode.NotFound,
                    mapOf(
                        "success" to false,
                        "error" to "Invalid mod"
                    )
                )
            try {
                call.respond(
                    HttpStatusCode.OK,
                    mapOf(
                        "success" to true,
                        "data" to mapOf(
                            "url" to resolveUrl(ModData(
                                version = version,
                                source = modData.source,
                                ref = modData.ref,
                                name = id,
                                id = modData.id
                            )),
                            "metadata" to mod.metadata
                        )
                    )
                )
            } catch (ignored: ModNotFoundException) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf(
                        "success" to false,
                        "error" to "Failed to fetch download url from source"
                    )
                )
            }
        }
        get("/v1/mods/{id}/{platform}/{minecraftVersion}/{version}/download") {
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
                    HttpStatusCode.NotFound,
                    mapOf(
                        "success" to false,
                        "error" to "Invalid mod"
                    )
                )
            try {
                call.respondRedirect(resolveUrl(ModData(
                    version = version,
                    source = modData.source,
                    ref = modData.ref,
                    name = id,
                    id = modData.id
                )), permanent = true)
            } catch (ignored: ModNotFoundException) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf(
                        "success" to false,
                        "error" to "Failed to fetch download url from source"
                    )
                )
            }
        }
    }
}
