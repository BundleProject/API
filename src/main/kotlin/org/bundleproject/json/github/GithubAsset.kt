package org.bundleproject.json.github

import com.google.gson.annotations.SerializedName

data class GithubAsset(@SerializedName("browser_download_url") val browserDownloadUrl: String)
