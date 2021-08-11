package org.bundleproject.utils

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.http.*

val httpClient = HttpClient(Apache) {
    install(JsonFeature) {
        serializer = GsonSerializer()
        // TODO: Use something other than raw.githubusercontent.com so we don't have to parse text/plain
        acceptContentTypes += ContentType("text", "plain")
    }
}
const val assetsUrl = "https://raw.githubusercontent.com/TymanWasTaken/Assets/patch-1/assets/mods.json"
const val githubApiUrl = "https://api.github.com/repos"