package org.bundleproject

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.utils.io.charsets.*
import kotlinx.coroutines.runBlocking
import org.bundleproject.json.assets.ModAssets
import org.bundleproject.json.responses.ModResponse
import org.bundleproject.plugins.configureRouting
import org.bundleproject.plugins.configureSerialization
import org.bundleproject.utils.fetchAssets
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testMod() {
        withTestApplication({
            configureRouting()
            configureSerialization()
        }) {
            var assets: ModAssets
            runBlocking {
                assets = fetchAssets()
            }
            val id = assets.mods.keys.first()
            val asset = assets.mods[id]!!
            val platform = asset.platforms.keys.first()
            val mcVer = asset.platforms[platform]!!.keys.first()
            val modVer = asset.platforms[platform]!![mcVer]!!.keys.first()
            val url = "/v1/mods/$id/$platform/$mcVer/$modVer"
            handleRequest(HttpMethod.Get, url) {
                addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                Gson().fromJson(response.content, ModResponse::class.java)
            }
        }
    }

    @Test
    fun testModDownload() {
        withTestApplication({
            configureRouting()
            configureSerialization()
        }) {
            var assets: ModAssets
            runBlocking {
                assets = fetchAssets()
            }
            val id = assets.mods.keys.first()
            val asset = assets.mods[id]!!
            val platform = asset.platforms.keys.first()
            val mcVer = asset.platforms[platform]!!.keys.first()
            val modVer = asset.platforms[platform]!![mcVer]!!.keys.first()
            val url = "/v1/mods/$id/$platform/$mcVer/$modVer/download"
            handleRequest(HttpMethod.Get, url).apply {
                assertEquals(HttpStatusCode.MovedPermanently, response.status())
            }
        }
    }
}