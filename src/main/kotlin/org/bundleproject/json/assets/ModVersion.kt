package org.bundleproject.json.assets

import com.google.gson.annotations.SerializedName

enum class ModSource {
    @SerializedName("github")
    GITHUB,
    @SerializedName("modrinth")
    MODRINTH,
    @SerializedName("curseforge")
    CURSEFORGE,
    @SerializedName("direct")
    DIRECT
}

data class ModVersion(
    var source: ModSource,
    val ref: String
)
