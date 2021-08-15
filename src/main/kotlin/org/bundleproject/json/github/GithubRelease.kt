package org.bundleproject.json.github

import com.google.gson.annotations.SerializedName

data class GithubRelease(
    val assets: List<GithubAsset>,
    @SerializedName("tag_name")
    val tagName: String,
)