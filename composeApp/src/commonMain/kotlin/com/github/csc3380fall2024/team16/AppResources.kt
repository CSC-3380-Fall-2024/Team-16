package com.github.csc3380fall2024.team16

import com.github.csc3380fall2024.team16.repository.FoodLogsRepository
import com.github.csc3380fall2024.team16.repository.NewsRepository
import com.github.csc3380fall2024.team16.repository.PostsRepository
import com.github.csc3380fall2024.team16.repository.SessionRepository
import com.github.csc3380fall2024.team16.repository.WorkoutLogsRepository
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.Url
import kotlinx.io.files.Path

class AppResources(val backendUrl: Url, appDir: String) {
    val client = RpcClient(URLBuilder(backendUrl).apply {
        protocol = URLProtocol.WS
        pathSegments = listOf("rpc")
    }.build())
    val sessionRepo = SessionRepository(client, Path(appDir, "session"))
    val newsRepo = NewsRepository(client, Path(appDir, "news"))
    val foodLogsRepo = FoodLogsRepository(client, Path(appDir, "foodLogs"))
    val workoutLogsRepo = WorkoutLogsRepository(client, Path(appDir, "workoutLogs"))
    val postsRepo = PostsRepository(client, Path(appDir, "posts"))
}
