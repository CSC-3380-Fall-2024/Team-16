package com.github.csc3380fall2024.team16

import com.github.csc3380fall2024.team16.plugins.configureDatabase
import com.github.csc3380fall2024.team16.plugins.configureRpc
import com.github.csc3380fall2024.team16.repository.NewsRepository
import com.github.csc3380fall2024.team16.server.BuildConfig
import io.ktor.server.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer


fun main() {
    embeddedServer(
        factory = CIO,
        port = BuildConfig.SERVER_PORT,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureDatabase()
    
    val newsRepo = NewsRepository(BuildConfig.BING_SEARCH_API_KEY)
    configureRpc(newsRepo)
}
