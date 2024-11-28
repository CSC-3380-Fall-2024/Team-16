package com.github.csc3380fall2024.team16

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.cancel
import kotlinx.rpc.krpc.ktor.client.installRPC
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.withService

// A wrapper around an rpc service.
// Needed because kotlinx.rpc does not yet support one-shot rpc calls.
class RpcClient(private val url: String = "ws://10.0.2.2:26542/rpc") {
    private val client = HttpClient(CIO) {
        installRPC()
    }
    
    suspend fun <T> rpc(fn: suspend RpcService.() -> T): RpcResult<T> {
        val rpc = try {
            client.rpc(url) {
                rpcConfig {
                    serialization {
                        json()
                    }
                }
            }
        } catch (e: Exception) {
            return RpcResult.ConnectionFailure(e)
        }
        
        val response = try {
            val service = rpc.withService<RpcService>()
            service.fn()
        } catch (e: Exception) {
            return RpcResult.ConnectionFailure(e)
        } finally {
            rpc.webSocketSession.cancel()
        }
        
        return RpcResult.Success(response)
    }
}

sealed class RpcResult<out T> {
    data class ConnectionFailure(val exception: Exception) : RpcResult<Nothing>()
    data class Success<T>(val value: T) : RpcResult<T>()
}
