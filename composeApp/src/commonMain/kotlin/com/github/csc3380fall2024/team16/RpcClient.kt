package com.github.csc3380fall2024.team16

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.timeout
import io.ktor.http.Url
import kotlinx.coroutines.cancel
import kotlinx.rpc.krpc.ktor.client.installRPC
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.withService

// A wrapper around an rpc service.
// Needed because kotlinx.rpc does not yet support one-shot rpc calls.
class RpcClient(private val url: Url) {
    private val client = HttpClient(CIO) {
        installRPC()
    }
    
    /**
     * @throws NetworkException
     */
    suspend fun <T> rpc(fn: suspend RpcService.() -> T): T {
        val rpc = try {
            client.rpc(url.toString()) {
                timeout { connectTimeoutMillis = 3000 }
                rpcConfig { serialization { json() } }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw NetworkException(e)
        }
        
        try {
            val service = rpc.withService<RpcService>()
            return service.fn()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        } finally {
            rpc.webSocketSession.cancel()
        }
    }
}

class NetworkException(cause: Throwable) : Exception(cause)
