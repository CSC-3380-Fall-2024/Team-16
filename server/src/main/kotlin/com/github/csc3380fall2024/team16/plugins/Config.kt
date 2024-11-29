package com.github.csc3380fall2024.team16.plugins

import kotlinx.io.IOException
import java.util.Properties


private val props = try {
    val prop = Properties()
    val loader = Thread.currentThread().contextClassLoader
    val stream = loader.getResourceAsStream("config.properties")
    prop.load(stream)
    prop
} catch (e: IOException) {
    null
}

fun config(name: String): String {
    if (props != null) {
        return props.getProperty(name)
    }
    
    return System.getenv(name) ?: throw Exception("environment variable $name not set")
}
