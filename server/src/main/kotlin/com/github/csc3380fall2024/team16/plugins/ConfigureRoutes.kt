package com.github.csc3380fall2024.team16.plugins

import com.github.csc3380fall2024.team16.repository.PostsRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respondBytes
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRoutes() {
    routing {
        get("/post_image/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.response.status(HttpStatusCode.NotFound)
                return@get
            }
            
            val img = transaction { PostsRepository.getPostImage(id) }
            if (img == null) {
                call.response.status(HttpStatusCode.NotFound)
                return@get
            }
            
            call.respondBytes(img)
        }
    }
}
