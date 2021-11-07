package org.bundleproject.api.utils

import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlinx.coroutines.runBlocking

class Cache<T>(val cacheTime: Long, private val retriever: suspend () -> T) :
    ReadOnlyProperty<Any, T> {
    private var lastUpdated: Date? = null
    private var cached: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        // Save to local variables for null safety
        val cached = this.cached
        val lastUpdated = this.lastUpdated
        if (cached == null || lastUpdated == null) {
            val fetched = runBlocking { retriever() }
            this.cached = fetched
            this.lastUpdated = Date()
            return fetched
        }
        return if (Date().time - lastUpdated.time >= cacheTime) {
            this.cached = runBlocking { retriever() }
            this.cached!!
        } else {
            this.cached!!
        }
    }
}
