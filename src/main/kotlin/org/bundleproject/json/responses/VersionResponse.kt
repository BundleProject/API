package org.bundleproject.json.responses

import org.bundleproject.json.assets.VersionAssets

data class VersionResponse(val success: Boolean = true, val data: VersionAssets)
