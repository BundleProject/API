package org.bundleproject.utils

import io.ktor.application.*
import io.ktor.client.request.*
import java.util.*
import org.bundleproject.json.ModData
import org.bundleproject.json.assets.ModAssets
import org.bundleproject.json.assets.ModSource
import org.bundleproject.json.github.GithubCommit
import org.bundleproject.json.github.GithubReleases
import org.bundleproject.json.modrinth.ModrinthMod
import org.bundleproject.json.modrinth.ModrinthModVersions

object AssetsCache {
    private var lastUpdated: Date? = null
    private var cached: ModAssets? = null

    suspend fun fetchAssets(): ModAssets {
        val (
            latestCommitId
        ) = httpClient.get<GithubCommit>(assetsLatestCommitUrl)
        return httpClient.get(getAssetsUrl(latestCommitId))
    }

    suspend fun getAssets(): ModAssets {
        // Save to local variables for null safety
        val cached = cached
        val lastUpdated = lastUpdated
        if (cached == null || lastUpdated == null) {
            val fetched = fetchAssets()
            this.cached = fetched
            this.lastUpdated = Date()
            return fetched
        }
        return if (Date().time - lastUpdated.time >= 5 * 60 * 1000) {
            this.cached = fetchAssets()
            this.cached!!
        } else {
            this.cached!!
        }
    }
}

suspend fun resolveUrl(modData: ModData): String {
    return when (modData.source) {
        ModSource.DIRECT -> modData.ref
        ModSource.GITHUB -> {
            val releases: GithubReleases =
                httpClient.get("$githubApiUrl/repos/${modData.ref}/releases")
            releases
                .find { it.tagName == modData.version || it.tagName == "v${modData.version}" }
                ?.assets
                ?.getOrNull(0)
                ?.browserDownloadUrl
                ?: throw ModNotFoundException()
        }
        ModSource.MODRINTH -> {
            var id = modData.id
            if (id == null) {
                val mod: ModrinthMod = httpClient.get("$modrinthApiUrl/mod/${modData.name}")
                id = mod.id
            }
            val modVersions: ModrinthModVersions =
                httpClient.get("$modrinthApiUrl/mod/${id}/version")
            modVersions.find { it.versionNumber == modData.version }?.files?.getOrNull(0)?.url
                ?: throw ModNotFoundException()
        }
    }
}

suspend fun getModFromCall(call: ApplicationCall): ModData {
    val assets = AssetsCache.getAssets()
    val id = call.parameters["id"]!!
    val platform = call.parameters["platform"]!!
    val minecraftVersion = call.parameters["minecraftVersion"]!!
    val version = call.parameters["version"]!!
    val mod = assets.mods[id]
    val modData =
        mod?.platforms?.get(platform)?.get(minecraftVersion)?.get(version)
            ?: throw ModNotFoundException()
    return ModData(
        version = version,
        source = modData.source,
        ref = modData.ref,
        name = id,
        id = modData.id,
        metadata = mod.metadata
    )
}
