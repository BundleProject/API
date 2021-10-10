package org.bundleproject.json.responses

import org.bundleproject.json.assets.ModMetadata

data class ModResponseData(val url: String, val version: String, val metadata: ModMetadata)
