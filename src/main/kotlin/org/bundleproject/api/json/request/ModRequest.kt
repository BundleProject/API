package org.bundleproject.api.json.request

import io.ktor.application.*

data class ModRequest(
    val id: String,
    val platform: String,
    val minecraftVersion: String,
    val version: String,
) {
    constructor(
        call: ApplicationCall
    ) : this(
        call.parameters["id"]!!,
        call.parameters["platform"]!!,
        call.parameters["minecraftVersion"]!!,
        call.parameters["version"]!!
    )
}
