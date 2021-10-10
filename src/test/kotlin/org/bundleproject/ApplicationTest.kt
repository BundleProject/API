package org.bundleproject

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.utils.io.charsets.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import org.bundleproject.json.responses.ModResponse
import org.bundleproject.utils.AssetsCache
import org.bundleproject.utils.CannotFindTestModException

class ApplicationTest {
    private val gson = Gson()

    private suspend fun getTestRoute(latest: Boolean = false): String {
        val assets = AssetsCache.getAssets()
        val id = assets.mods.keys.first()
        val asset = assets.mods[id]
        val platform = asset?.platforms?.keys?.first()
        val mcVer = asset?.platforms?.get(platform)?.keys?.first()
        val modVer =
            if (latest) "latest"
            else
                asset?.platforms?.get(platform)?.get(mcVer)?.keys?.first()
                    ?: throw CannotFindTestModException()
        return "/v1/mods/$id/$platform/$mcVer/$modVer"
    }

    @Test
    fun testMod() {
        withTestApplication({ configurePlugins() }) {
            handleRequest(HttpMethod.Get, runBlocking { getTestRoute() }) {
                addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
            }
                .apply {
                    assertEquals(HttpStatusCode.OK, response.status())
                    assertEquals(
                        ContentType.Application.Json.withCharset(Charsets.UTF_8),
                        response.contentType()
                    )
                    gson.fromJson(response.content, ModResponse::class.java)
                }
        }
    }

    @Test
    fun testLatestMod() {
        withTestApplication({ configurePlugins() }) {
            handleRequest(HttpMethod.Get, runBlocking { getTestRoute(true) }) {
                addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
            }
                .apply {
                    assertEquals(HttpStatusCode.OK, response.status())
                    assertEquals(
                        ContentType.Application.Json.withCharset(Charsets.UTF_8),
                        response.contentType()
                    )
                    gson.fromJson(response.content, ModResponse::class.java)
                }
        }
    }

    @Test
    fun testModDownload() {
        withTestApplication({ configurePlugins() }) {
            handleRequest(HttpMethod.Get, runBlocking { getTestRoute() } + "/download").apply {
                assertEquals(HttpStatusCode.MovedPermanently, response.status())
            }
        }
    }
}
