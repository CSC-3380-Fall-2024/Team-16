package com.github.csc3380fall2024.team16

import UniversalFitness.composeApp.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.cancel
import kotlinx.rpc.krpc.ktor.client.KtorRPCClient
import kotlinx.rpc.krpc.ktor.client.installRPC
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.withService

// A wrapper around an rpc service.
// Needed because kotlinx.rpc does not yet support one-shot rpc calls.
class RpcClient(private val url: String = "ws://${BuildConfig.SERVER_IP}:${BuildConfig.SERVER_PORT}/rpc") {
    private val client = HttpClient(CIO) {
        installRPC()
    }
    
    suspend fun <T> rpc(fn: suspend RpcService.() -> T): T {
        var rpc: KtorRPCClient? = null
        
        try {
            rpc = client.rpc(url) {
                rpcConfig {
                    serialization {
                        json()
                    }
                }
            }
            
            val service = rpc.withService<RpcService>()
            return service.fn()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        } finally {
            rpc?.webSocketSession?.cancel()
        }
    }
}
