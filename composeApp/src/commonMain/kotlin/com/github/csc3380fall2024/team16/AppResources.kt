package com.github.csc3380fall2024.team16

import com.github.csc3380fall2024.team16.repository.SessionRepository
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.Url
import kotlinx.io.files.Path

class AppResources(backendUrl: Url, appDir: String) {
    val client = RpcClient(URLBuilder(backendUrl).apply {
        protocol = URLProtocol.WS
        pathSegments = listOf("rpc")
    }.build())
    val sessionRepo = SessionRepository(client, Path(appDir, "session"))
}
