package org.bundleproject

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import org.bundleproject.json.assets.ModAssets
import org.bundleproject.plugins.configureRouting
import org.bundleproject.utils.fetchAssets
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testMod() {
        withTestApplication({ configureRouting() }) {
            var assets: ModAssets
            runBlocking {
                assets = fetchAssets()
            }
            val asset = assets.mods[assets.mods.keys.first()]!!
            handleRequest(HttpMethod.Get, "/v1/mods/${asset.platforms.keys.first()}").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }
}