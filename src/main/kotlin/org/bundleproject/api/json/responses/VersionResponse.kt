package org.bundleproject.api.json.responses

import org.bundleproject.api.json.assets.VersionAssets

data class VersionResponse(val success: Boolean = true, val data: VersionAssets)
