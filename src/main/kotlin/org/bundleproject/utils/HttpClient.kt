package org.bundleproject.utils

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.http.*

val httpClient =
    HttpClient(Apache) {
        install(JsonFeature) {
            serializer = GsonSerializer()
            // TODO: Use a cdn so we don't have to parse text/plain as json
            acceptContentTypes += ContentType("text", "plain")
        }
    }
