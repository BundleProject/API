package org.bundleproject.json.responses

data class ErrorResponse(
    val success: Boolean = false,
    val error: String
)