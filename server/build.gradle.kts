val envVars = listOf("SERVER_PORT")

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "com.github.csc3380fall2024.team16"
version = "1.0.0"
application {
    mainClass = "com.github.csc3380fall2024.team16.ApplicationKt"
}

tasks.register("generateDevelopmentConfig") {
    val resourcesDir = layout.buildDirectory.dir("resources/main").get().asFile
    inputs.file(rootDir.resolve(".env"))
    outputs.dir(resourcesDir)
    
    doLast {
        val file = resourcesDir.resolve("config.properties")
        file.parentFile.mkdirs()
        
        var text = ""
        envVars.forEach { text += "$it = ${env.fetch(it)}\n" }
        file.writeText(text)
    }
}

tasks.compileJava {
    dependsOn("generateDevelopmentConfig")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.java.jwt)
    implementation(libs.kotlinx.rpc.krpc.server)
    implementation(libs.kotlinx.rpc.krpc.ktor.server)
    implementation(libs.kotlinx.rpc.krpc.serialization.json)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.postgresql)
}
