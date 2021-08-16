package org.bundleproject.utils

import io.ktor.application.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.response.*
import org.bundleproject.json.ModData
import org.bundleproject.json.assets.ModAssets
import org.bundleproject.json.assets.ModSource
import org.bundleproject.json.github.GithubReleases
import org.bundleproject.json.modrinth.ModrinthMod
import org.bundleproject.json.modrinth.ModrinthModVersions

suspend fun fetchAssets(): ModAssets = httpClient.get(assetsUrl)

suspend fun resolveUrl(modData: ModData): String {
    return when (modData.source) {
        ModSource.DIRECT -> modData.ref
        ModSource.GITHUB -> {
            val releases: GithubReleases =
                httpClient.get("$githubApiUrl/repos/${modData.ref}/releases")
            releases.find { it.tagName == modData.version }?.assets?.get(0)?.browserDownloadUrl
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
            modVersions.find { it.versionNumber == modData.version }?.files?.get(0)?.url
                ?: throw ModNotFoundException()
        }
    }
}

suspend fun getModFromCall(call: ApplicationCall): ModData {
    val assets = fetchAssets()
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
