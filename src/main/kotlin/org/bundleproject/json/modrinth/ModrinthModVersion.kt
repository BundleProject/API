package org.bundleproject.json.modrinth

import com.google.gson.annotations.SerializedName

data class ModrinthModVersion(
    val files: List<ModrinthFile>,
    val name: String,
    @SerializedName("version_number")
    val versionNumber: String,
)