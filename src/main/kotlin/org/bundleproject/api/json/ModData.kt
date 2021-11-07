package org.bundleproject.api.json

import org.bundleproject.api.json.assets.ModMetadata
import org.bundleproject.api.json.assets.ModSource

data class ModData(
    val version: String,
    val source: ModSource,
    val ref: String,
    val name: String,
    val metadata: ModMetadata,
    val id: String?
)
