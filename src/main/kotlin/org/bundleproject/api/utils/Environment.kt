/**
 * Taken using MIT license from kord-extensions
 * https://github.com/Kord-Extensions/kord-extensions/blob/7ecf3dee053fdcf387a04a7a5c4320092160282e/kord-extensions/src/main/kotlin/com/kotlindiscord/kord/extensions/utils/_Environment.kt
 */
package org.bundleproject.api.utils

import kotlin.io.path.Path
import kotlin.io.path.isRegularFile
import kotlin.io.path.readLines

private var firstLoad: Boolean = true
private val envMap: MutableMap<String, String> = mutableMapOf()

/**
 * Returns the value of an environmental variable, loading from a `.env` file in the current working
 * directory if possible.
 *
 * This function caches the contents of the `.env` file the first time it's called - there's no way
 * to parse the file again later.
 *
 * @param name Environmental variable to get the value for.
 * @return The value of the environmental variable, or `null` if it doesn't exist.
 */
fun env(name: String): String? {
    if (firstLoad) {
        firstLoad = false

        val dotenvFile = Path(".env")

        if (dotenvFile.isRegularFile()) {
            val lines = dotenvFile.readLines()

            for (line in lines) {
                var effectiveLine = line.trimStart()

                if (effectiveLine.startsWith("#")) {
                    continue
                }

                if (effectiveLine.contains("#")) {
                    effectiveLine = effectiveLine.substring(0, effectiveLine.indexOf("#"))
                }

                if (!effectiveLine.contains('=')) {
                    continue
                }

                val split = effectiveLine.split("=", limit = 2)

                if (split.size != 2) {
                    continue
                }

                envMap[split[0]] = split[1]
            }
        }
    }

    return envMap[name] ?: System.getenv()[name]
}
