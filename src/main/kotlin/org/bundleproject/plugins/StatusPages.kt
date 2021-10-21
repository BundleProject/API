package org.bundleproject.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import org.bundleproject.json.responses.ErrorResponse
import org.bundleproject.utils.ModDownloadNotAvailableException
import org.bundleproject.utils.ModNotFoundException
import org.bundleproject.utils.NoAuthenticationException

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
        exception<NoAuthenticationException> {
            call.respond(
                HttpStatusCode.NonAuthoritativeInformation,
                ErrorResponse(error = "Incorrect auth!")
            )
        }
    }
}
