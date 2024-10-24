package com.github.csc3380fall2024.team16

import io.ktor.server.application.Application
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.TextColumnType
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestampWithTimeZone
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    val url = "jdbc:postgresql://localhost:5432/universalfitness"
    val user = "universalfitness"
    val password = "universalfitness"
    
    Database.connect(url, user = user, password = password)
    
    transaction {
        SchemaUtils.create(Users, Friends, Comments, PostLikes, CommentLikes, ExerciseLogs, PersonalRecords)
    }
}

object Users : UUIDTable("users") {
    val username = varchar("username", 24)
    val email = varchar("email", 255)
    val passwordHash = varchar("password_hash", 255)
}

object Friends : Table("friends") {
    val user1 = reference("user1", Users, onDelete = ReferenceOption.CASCADE)
    val user2 = reference("user2", Users, onDelete = ReferenceOption.CASCADE)
    val accepted = bool("accepted")
    val since = timestampWithTimeZone("since").defaultExpression(CurrentTimestampWithTimeZone)
}

object Posts : UUIDTable("posts") {
    val user = reference("user", Users, onDelete = ReferenceOption.CASCADE)
    val description = varchar("description", 2000)
    val photos = array("files", TextColumnType(), 20)
}

object Comments : UUIDTable("comments") {
    val post = reference("post", Posts, onDelete = ReferenceOption.CASCADE)
    val user = reference("user", Users, onDelete = ReferenceOption.CASCADE)
    val text = varchar("text", 2000)
}

object PostLikes : Table("post_likes") {
    val post = reference("post", Posts, onDelete = ReferenceOption.CASCADE)
    val user = reference("user", Users, onDelete = ReferenceOption.CASCADE)
}

object CommentLikes : Table("comment_likes") {
    val comment = reference("comment", Comments, onDelete = ReferenceOption.CASCADE)
    val user = reference("user", Users, onDelete = ReferenceOption.CASCADE)
}

object ExerciseLogs : UUIDTable("workout_logs") {
    val user = reference("user", Users, onDelete = ReferenceOption.CASCADE)
    val exercise = varchar("exercise", 255)
    val timestamp = timestampWithTimeZone("since").defaultExpression(CurrentTimestampWithTimeZone)
}

object PersonalRecords : Table("personal_records") {
    val user = reference("user", Users, onDelete = ReferenceOption.CASCADE)
    val exercise = varchar("exercise", 255)
    val pounds = float("pounds")
}
