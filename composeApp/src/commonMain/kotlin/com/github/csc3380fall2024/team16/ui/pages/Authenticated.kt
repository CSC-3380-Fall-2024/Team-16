package com.github.csc3380fall2024.team16.ui.pages

import kotlinx.serialization.Serializable

@Serializable
data class Authenticated(val token: String)