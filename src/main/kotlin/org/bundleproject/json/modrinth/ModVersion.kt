package org.bundleproject.json.modrinth

data class ModVersion(
    val author_id: String,
    val changelog: String,
    val changelog_url: Any,
    val date_published: String,
    val dependencies: List<Any>,
    val downloads: Int,
    val featured: Boolean,
    val files: List<File>,
    val game_versions: List<String>,
    val id: String,
    val loaders: List<String>,
    val mod_id: String,
    val name: String,
    val version_number: String,
    val version_type: String
)