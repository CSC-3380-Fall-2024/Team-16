package com.github.csc3380fall2024.team16

class Greeting {
    private val platform = getPlatform()
    
    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}
