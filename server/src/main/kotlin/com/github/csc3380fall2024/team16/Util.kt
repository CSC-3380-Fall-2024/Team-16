package com.github.csc3380fall2024.team16

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

// returns 16 byte salt
fun createRandomSalt(): ByteArray {
    val random = SecureRandom()
    val salt = ByteArray(16)
    random.nextBytes(salt)
    return salt
}

// returns 128 byte hash
fun createHash(password: String, salt: ByteArray): ByteArray {
    val spec = PBEKeySpec(password.toCharArray(), salt, 65536, 128)
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    return factory.generateSecret(spec).encoded
}

val usernameRegex = Regex("^[A-Za-z0-9._]+\$")
val emailRegex =
    Regex("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
