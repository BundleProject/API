package org.bundleproject.api.json.responses

import org.bundleproject.api.json.assets.VersionAsset

data class VersionResponse(val success: Boolean = true, val data: VersionAsset)
