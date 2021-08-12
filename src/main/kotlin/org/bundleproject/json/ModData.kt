package org.bundleproject.json

import org.bundleproject.json.assets.ModSource

data class ModData(
    val version: String,
    val source: ModSource,
    val ref: String,
    val name: String,
    val id: String?
)