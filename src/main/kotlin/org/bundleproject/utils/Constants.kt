package org.bundleproject.utils

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.http.*

val port = System.getenv("PORT")?.toIntOrNull() ?: 8080

val httpClient =
    HttpClient(Apache) {
        install(JsonFeature) {
            serializer = GsonSerializer()
            // TODO: Use something other than raw.githubusercontent.com so we don't have to parse
            // text/plain
            acceptContentTypes += ContentType("text", "plain")
        }
    }
const val assetsUrl = "https://raw.githubusercontent.com/BundleProject/Assets/main/assets/mods.json"
const val githubApiUrl = "https://api.github.com"
const val modrinthApiUrl = "https://api.modrinth.com/api/v1"
