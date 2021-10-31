package org.bundleproject.utils

val port = env("PORT")?.toIntOrNull() ?: 8080
val githubAuth =
    listOf(
        env("GITHUB_AUTH_USER") ?: error("No github username/clientid provided"),
        env("GITHUB_AUTH_PASS") ?: error("No github password/secret provided")
    )

fun getAssetsUrl(version: String, path: String) = "$jsDelivrUrl$version/assets/$path"

private const val jsDelivrUrl = "https://cdn.jsdelivr.net/gh/BundleProject/Assets@"
const val githubApiUrl = "https://api.github.com"
const val assetsLatestCommitUrl = "$githubApiUrl/repos/BundleProject/Assets/commits/main"
const val modrinthApiUrl = "https://api.modrinth.com/api/v1"
