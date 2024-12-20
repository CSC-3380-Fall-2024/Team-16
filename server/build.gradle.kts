plugins {
    alias(libs.plugins.buildconfig)
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
    alias(libs.plugins.dockerCompose)
    application
}

group = "com.github.csc3380fall2024.team16"
version = "1.0.0"
application {
    mainClass = "com.github.csc3380fall2024.team16.ApplicationKt"
}

buildConfig {
    buildConfigField("SERVER_PORT", env.fetch("SERVER_PORT").toInt())
    buildConfigField("DATABASE_PORT", env.fetch("DATABASE_PORT").toInt())
    buildConfigField("JWT_SECRET", env.fetch("JWT_SECRET"))
    buildConfigField("BING_SEARCH_API_KEY", env.fetch("BING_SEARCH_API_KEY"))
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.java.jwt)
    implementation(libs.kotlinx.rpc.krpc.server)
    implementation(libs.kotlinx.rpc.krpc.ktor.server)
    implementation(libs.kotlinx.rpc.krpc.serialization.json)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.postgresql)
}
