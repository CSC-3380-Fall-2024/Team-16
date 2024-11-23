package com.github.csc3380fall2024.team16

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.rpc.krpc.ktor.client.installRPC
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.withService

suspend fun createRpcClient(): RpcService {
    val client = HttpClient(CIO) {
        installRPC()
    }
    
    return client.rpc("ws://10.0.2.2:26542/rpc") {
        rpcConfig {
            serialization {
                json()
            }
        }
    }.withService<RpcService>()
}
