package org.bundleproject.api.json.assets

import com.google.gson.annotations.SerializedName

data class VersionAsset(
    val updater: String,
    @SerializedName("launchwrapper") val launchWrapper: String,
    val installer: String,
)
