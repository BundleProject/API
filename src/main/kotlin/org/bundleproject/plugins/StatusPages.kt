package org.bundleproject.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import org.bundleproject.json.responses.ErrorResponse
import org.bundleproject.utils.ModNotFoundException

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<ModNotFoundException> {
             call.respond(
                 HttpStatusCode.InternalServerError,
                 ErrorResponse(
                     error = "Unable to find mod"
                 )
             )
        }
    }
}