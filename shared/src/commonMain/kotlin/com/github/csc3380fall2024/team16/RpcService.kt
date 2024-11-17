package com.github.csc3380fall2024.team16

import kotlinx.rpc.RemoteService
import kotlinx.rpc.annotations.Rpc

@Rpc
interface RpcService : RemoteService {
    suspend fun register(username: String, email: String, password: String): String
    suspend fun login(usernameOrEmail: String, password: String): String
}
