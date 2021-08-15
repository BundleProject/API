package org.bundleproject.json.responses

data class ErrorResponse(
    val error: String,
) {
    val success = false
}