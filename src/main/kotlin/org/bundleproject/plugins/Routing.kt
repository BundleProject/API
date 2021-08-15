package org.bundleproject.plugins

import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import org.bundleproject.json.ModData
import org.bundleproject.json.responses.ErrorResponse
import org.bundleproject.json.responses.ModResponse
import org.bundleproject.json.responses.ModResponseData
import org.bundleproject.utils.ModNotFoundException
import org.bundleproject.utils.fetchAssets
import org.bundleproject.utils.getModFromCall
import org.bundleproject.utils.resolveUrl

fun Application.configureRouting() {
    routing {
        get("/") {
            TODO("Send docs here")
        }
        get("/v1/mods/{id}/{platform}/{minecraftVersion}/{version}") {
            val modData = getModFromCall(call)
            call.respond(
                HttpStatusCode.OK,
                ModResponse(
                    data = ModResponseData(
                        url = resolveUrl(modData),
                        metadata = modData.metadata
                    )
                )
            )
        }
        get("/v1/mods/{id}/{platform}/{minecraftVersion}/{version}/download") {
            val modData = getModFromCall(call)
            call.respondRedirect(
                permanent = true,
                url = resolveUrl(modData)
            )
        }
    }
}
