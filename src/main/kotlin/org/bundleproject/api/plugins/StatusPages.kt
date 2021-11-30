package org.bundleproject.api.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import org.bundleproject.api.json.responses.ErrorResponse
import org.bundleproject.api.utils.ModDownloadNotAvailableException
import org.bundleproject.api.utils.ModNotFoundException

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<ModNotFoundException> {
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(error = "Unable to find mod")
            )
        }
        exception<ModDownloadNotAvailableException> {
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(error = "Could not find mod download")
            )
        }
        exception<Exception> {
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(
                    error = "${it.javaClass.name}: ${it.message ?: "no error message provided"}"
                )
            )
        }
    }
}
