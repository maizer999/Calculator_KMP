package org.sample.calculator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform