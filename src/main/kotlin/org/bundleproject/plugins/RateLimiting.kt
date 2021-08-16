package org.bundleproject.plugins

import guru.zoroark.ratelimit.RateLimit
import io.ktor.application.*
import java.time.Duration

fun Application.configureRateLimiting() {
    install(RateLimit) {
        limit = 10
        timeBeforeReset = Duration.ofSeconds(30)
    }
}
