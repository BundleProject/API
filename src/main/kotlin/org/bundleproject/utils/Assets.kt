package org.bundleproject.utils

import io.ktor.client.request.*
import org.bundleproject.json.ModData
import org.bundleproject.json.assets.ModAssets
import org.bundleproject.json.assets.ModSource
import org.bundleproject.json.github.GithubReleases
import org.bundleproject.json.modrinth.Mod
import org.bundleproject.json.modrinth.ModVersions

suspend fun fetchAssets(): ModAssets = httpClient.get(assetsUrl)
suspend fun resolveUrl(modData: ModData): String {
    return when (modData.source) {
        ModSource.DIRECT -> modData.ref
        ModSource.GITHUB -> {
            val releases: GithubReleases = httpClient.get("$githubApiUrl/repos/${modData.ref}/releases")
            releases.find {
                it.tag_name == modData.version
            }?.assets?.get(0)?.browser_download_url ?: throw ModNotFoundException()
        }
        ModSource.MODRINTH -> {
            var id = modData.id
            if (id == null) {
                val mod: Mod = httpClient.get("$modrinthApiUrl/mod/${modData.name}")
                id = mod.id
            }
            val modVersions: ModVersions = httpClient.get("$modrinthApiUrl/mod/${id}/version")
            modVersions.find {
                it.version_number == modData.version
            }?.files?.get(0)?.url ?: throw ModNotFoundException()
        }
    }
}