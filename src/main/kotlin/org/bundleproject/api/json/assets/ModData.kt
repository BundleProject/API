package org.bundleproject.api.json.assets

import com.google.gson.annotations.SerializedName

enum class ModSource {
    @SerializedName("github") GITHUB,
    @SerializedName("modrinth") MODRINTH,
    @SerializedName("direct") DIRECT
}

data class ModData(
    var source: ModSource,
    val ref: String,
    val id: String? // Used to skip a web request when resolving modrinth mods (completely optional)
)
