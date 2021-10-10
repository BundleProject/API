package org.bundleproject.utils

val port = env("PORT")?.toIntOrNull() ?: 8080
val githubAuth = listOf(
    env("GITHUB_AUTH_USER") ?: throw IllegalArgumentException("No github username/clientid provided"),
    env("GITHUB_AUTH_PASS") ?: throw IllegalArgumentException("No github password/secret provided")
)

fun getAssetsUrl(ref: String) = "$assetsUrlPrefix$ref$assetsUrlSuffix"

private const val assetsUrlPrefix = "https://cdn.jsdelivr.net/gh/TymanWasTaken/Assets@"
private const val assetsUrlSuffix = "/assets/mods.json"
const val githubApiUrl = "https://api.github.com"
const val assetsLatestCommitUrl = "$githubApiUrl/repos/BundleProject/Assets/commits/main"
const val modrinthApiUrl = "https://api.modrinth.com/api/v1"
