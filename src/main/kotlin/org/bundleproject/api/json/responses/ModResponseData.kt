package org.bundleproject.api.json.responses

import org.bundleproject.api.json.assets.ModMetadata

data class ModResponseData(val url: String, val version: String, val metadata: ModMetadata)
