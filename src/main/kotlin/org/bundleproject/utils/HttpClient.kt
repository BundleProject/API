package org.bundleproject.utils

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*

val httpClient =
    HttpClient(Apache) {
        install(JsonFeature) { serializer = GsonSerializer() }
        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials(username = githubAuth[0], password = githubAuth[1])
                }
                sendWithoutRequest { it.url.host == "api.github.com" }
            }
        }
    }
