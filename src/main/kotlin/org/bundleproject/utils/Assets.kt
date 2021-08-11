package org.bundleproject.utils

import io.ktor.client.request.*
import org.bundleproject.json.assets.ModAssets
import org.bundleproject.json.assets.ModSource
import org.bundleproject.json.assets.ModVersion

suspend fun fetchAssets(): ModAssets = httpClient.get(assetsUrl)
suspend fun resolveUrl(urlData: ModVersion): String {
    return when (urlData.source) {
        ModSource.DIRECT -> urlData.ref
        ModSource.GITHUB -> {
//            val releases =
            TODO()
        }
        ModSource.MODRINTH -> TODO()
        ModSource.CURSEFORGE -> TODO()
    }
}