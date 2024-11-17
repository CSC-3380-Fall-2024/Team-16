package com.github.csc3380fall2024.team16

import com.github.csc3380fall2024.team16.plugins.configureDatabase
import com.github.csc3380fall2024.team16.plugins.configureRpc
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDatabase()
    configureRpc()
}
