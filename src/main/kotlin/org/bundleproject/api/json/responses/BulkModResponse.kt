package org.bundleproject.api.json.responses

data class BulkModResponse(val success: Boolean = true, val mods: List<ModResponseData>)
