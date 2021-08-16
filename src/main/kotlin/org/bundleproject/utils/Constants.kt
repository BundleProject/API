package org.bundleproject.utils

val port = System.getenv("PORT")?.toIntOrNull() ?: 8080

const val assetsUrl = "https://raw.githubusercontent.com/BundleProject/Assets/main/assets/mods.json"
const val githubApiUrl = "https://api.github.com"
const val modrinthApiUrl = "https://api.modrinth.com/api/v1"
