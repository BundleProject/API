package org.bundleproject.api.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
import io.ktor.application.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.response.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking
import org.bundleproject.api.json.ModData
import org.bundleproject.api.json.assets.ModAssets
import org.bundleproject.api.json.assets.ModSource
import org.bundleproject.api.json.assets.VersionAsset
import org.bundleproject.api.json.assets.VersionAssets
import org.bundleproject.api.json.github.GithubCommit
import org.bundleproject.api.json.github.GithubReleases
import org.bundleproject.api.json.modrinth.ModrinthMod
import org.bundleproject.api.json.modrinth.ModrinthModVersions
import org.bundleproject.api.json.request.ModRequest
import org.bundleproject.api.json.responses.ErrorResponse
import org.bundleproject.api.json.responses.ModResponseData
import org.bundleproject.libversion.Version

object AssetsCache {
    val modAssets by
        Cache<ModAssets>(TimeUnit.MINUTES.toMillis(5)) {
            val (latestCommitId) = httpClient.get<GithubCommit>(assetsLatestCommitUrl)
            httpClient.get(getAssetsUrl(latestCommitId, "mods.json"))
        }
    val versionAssets by
        Cache(TimeUnit.MINUTES.toMillis(5)) {
            fun getLatest(githubProject: String) = runBlocking {
                val releases =
                    httpClient.get<GithubReleases>("$githubApiUrl/repos/$githubProject/releases")

                Pair(
                    releases.firstOrNull { !it.prerelease }?.tagName ?: "unknown",
                    releases.firstOrNull { it.prerelease }?.tagName ?: "unknown",
                )
            }

            val updater = getLatest("BundleProject/Bundle")
            val launchWrapper = getLatest("BundleProject/Launchwrapper")
            val installer = getLatest("BundleProject/Installer")

            VersionAssets(
                release =
                    VersionAsset(
                        updater = updater.first,
                        launchWrapper = launchWrapper.first,
                        installer = installer.first,
                    ),
                prerelease =
                    VersionAsset(
                        updater = updater.second,
                        launchWrapper = launchWrapper.second,
                        installer = installer.second,
                    )
            )
        }
}

suspend fun resolveUrl(modData: ModData): String {
    return when (modData.source) {
        ModSource.DIRECT -> modData.ref
        ModSource.GITHUB -> {
            val releases: GithubReleases =
                try {
                    httpClient.get("$githubApiUrl/repos/${modData.ref}/releases")
                } catch (e: ClientRequestException) {
                    e.printStackTrace()
                    throw ModDownloadNotAvailableException()
                }

            releases
                .find { it.tagName == modData.version || it.tagName == "v${modData.version}" }
                ?.assets
                ?.getOrNull(0)
                ?.browserDownloadUrl
                ?: throw ModNotFoundException()
        }
        ModSource.MODRINTH -> {
            val id =
                try {
                    httpClient.get<ModrinthMod>("$modrinthApiUrl/mod/${modData.ref}").id
                } catch (e: ClientRequestException) {
                    e.printStackTrace()
                    throw ModDownloadNotAvailableException()
                }

            val modVersions: ModrinthModVersions =
                try {
                    httpClient.get("$modrinthApiUrl/mod/$id/version")
                } catch (e: ClientRequestException) {
                    e.printStackTrace()
                    throw ModDownloadNotAvailableException()
                }

            modVersions.find { it.versionNumber == modData.version }?.files?.getOrNull(0)?.url
                ?: throw ModNotFoundException()
        }
    }
}

suspend fun getModFromRequest(request: ModRequest): ModData {
    if (request.version == "latest") return getLatestModFromRequest(request)

    val assets = AssetsCache.modAssets
    val id = request.id
    val platform = request.platform
    val minecraftVersion = request.minecraftVersion
    val version = request.version
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

suspend fun getLatestModFromRequest(request: ModRequest): ModData {
    val assets = AssetsCache.modAssets
    val id = request.id
    val platform = request.platform
    val minecraftVersion = request.minecraftVersion
    val mod = assets.mods[id]
    val (version, modData) =
        mod?.platforms
            ?.get(platform)
            ?.get(minecraftVersion)
            ?.entries
            ?.sortedWith { o1, o2 -> Version.of(o2.key).compareTo(Version.of(o1.key)) }
            ?.first()
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

suspend fun getBulkMods(call: ApplicationCall): List<ModResponseData> {
    val bulk = call.parameters["bulk"]
    if (bulk == null)
        call.respond(HttpStatusCode.BadRequest, ErrorResponse(error = "please specify bulk"))

    val mods = mutableListOf<ModResponseData>()
    for (element in
        JsonParser.parseString(Base64.getDecoder().decode(bulk).decodeToString()).asJsonArray) {
        val request = Gson().fromJson(element, ModRequest::class.java)
        val modData = getModFromRequest(request)
        mods.add(
            ModResponseData(
                url = resolveUrl(modData),
                version = modData.version,
                metadata = modData.metadata,
            )
        )
    }

    return mods
}
