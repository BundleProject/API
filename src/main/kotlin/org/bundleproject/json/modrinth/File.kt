package org.bundleproject.json.modrinth

data class File(
    val filename: String,
    val hashes: Hashes,
    val primary: Boolean,
    val url: String
)