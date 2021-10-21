package org.bundleproject.utils

val port = env("PORT")?.toIntOrNull() ?: 8080
val githubAuth =
    listOf(
        env("GITHUB_AUTH_USER") ?: error("No github username/clientid provided"),
        env("GITHUB_AUTH_PASS") ?: error("No github password/secret provided")
    )
val authentication = env("INTERNAL_AUTH")

fun getAssetsUrl(ref: String) = "$assetsUrlPrefix$ref$assetsUrlSuffix"

private const val assetsUrlPrefix = "https://cdn.jsdelivr.net/gh/BundleProject/Assets@"
private const val assetsUrlSuffix = "/assets/mods.json"
const val githubApiUrl = "https://api.github.com"
const val assetsLatestCommitUrl = "$githubApiUrl/repos/BundleProject/Assets/commits/main"
const val modrinthApiUrl = "https://api.modrinth.com/api/v1"
