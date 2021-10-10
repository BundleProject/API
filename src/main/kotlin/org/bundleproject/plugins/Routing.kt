package org.bundleproject.plugins

import guru.zoroark.ratelimit.rateLimited
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.launch
import org.bundleproject.json.responses.ModResponse
import org.bundleproject.json.responses.ModResponseData
import org.bundleproject.utils.AssetsCache
import org.bundleproject.utils.getModFromCall
import org.bundleproject.utils.resolveUrl
import java.nio.file.Paths

fun Application.configureRouting() {
    // Preload assets in background
    launch {
        AssetsCache.getAssets()
    }
    // Configure routes
    routing {
        static("static") { resources("static") }
        get("/") {
            call.respondFile(
                Paths.get(this::class.java.getResource("/static/docs.html")!!.toURI()).toFile()
            )
        }
        get("/docs") {
            call.respondFile(
                Paths.get(this::class.java.getResource("/static/docs.html")!!.toURI()).toFile()
            )
        }
        rateLimited {
            get("/v1/mods/{id}/{platform}/{minecraftVersion}/{version}") {
                val modData = getModFromCall(call)
                call.respond(
                    HttpStatusCode.OK,
                    ModResponse(
                        data =
                        ModResponseData(url = resolveUrl(modData), metadata = modData.metadata)
                    )
                )
            }
            get("/v1/mods/{id}/{platform}/{minecraftVersion}/{version}/download") {
                val modData = getModFromCall(call)
                call.respondRedirect(permanent = true, url = resolveUrl(modData))
            }
        }
    }
}
