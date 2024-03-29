package org.bundleproject.api.plugins

import guru.zoroark.ratelimit.rateLimited
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.launch
import org.bundleproject.api.json.request.ModRequest
import org.bundleproject.api.json.responses.BulkModResponse
import org.bundleproject.api.json.responses.ModResponse
import org.bundleproject.api.json.responses.ModResponseData
import org.bundleproject.api.json.responses.VersionResponse
import org.bundleproject.api.utils.*

fun Application.configureRouting() {
    // Preload assets in background
    launch { AssetsCache.modAssets }
    // Configure routes
    routing {
        rateLimited {
            get("/v1/mods/{id}/{platform}/{minecraftVersion}/{version}") {
                val modData = getModFromRequest(ModRequest(call))
                call.respond(
                    HttpStatusCode.OK,
                    ModResponse(
                        data =
                            ModResponseData(
                                url = resolveUrl(modData),
                                version = modData.version,
                                metadata = modData.metadata,
                            )
                    )
                )
            }

            get("/v1/mods/{id}/{platform}/{minecraftVersion}/{version}/download") {
                val modData = getModFromRequest(ModRequest(call))
                call.respondRedirect(permanent = true, url = resolveUrl(modData))
            }

            get("/v1/mods/bulk/{bulk}") {
                call.respond(HttpStatusCode.OK, BulkModResponse(mods = getBulkMods(call)))
            }

            get("/v1/bundle/version") {
                call.respond(HttpStatusCode.OK, VersionResponse(data = AssetsCache.versionAssets))
            }
        }
    }
}
