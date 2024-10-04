package com.github.csc3380fall2024.team16

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform