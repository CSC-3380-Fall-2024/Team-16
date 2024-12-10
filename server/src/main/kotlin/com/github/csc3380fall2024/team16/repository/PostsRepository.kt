package com.github.csc3380fall2024.team16.repository

import com.github.csc3380fall2024.team16.Post
import com.github.csc3380fall2024.team16.plugins.Posts
import com.github.csc3380fall2024.team16.plugins.Users
import kotlinx.datetime.toKotlinInstant
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insertAndGetId
import java.util.UUID

object PostsRepository {
    fun getPosts(): List<Post> {
        val rows = Posts.join(Users, joinType = JoinType.LEFT, onColumn = Posts.user, otherColumn = Users.id)
            .select(Posts.id, Users.username, Posts.description, Posts.timestamp)
            .limit(20)
            .orderBy(Posts.timestamp, order = SortOrder.DESC)
        
        return rows.map {
            Post(
                id = it[Posts.id].value,
                user = it[Users.username],
                description = it[Posts.description],
                timestamp = it[Posts.timestamp].toInstant().toKotlinInstant(),
            )
        }
    }
    
    fun addPost(user: UUID, description: String, image: ByteArray): Int {
        return Posts.insertAndGetId {
            it[Posts.user] = user
            it[Posts.description] = description
            it[Posts.image] = image
        }.value
    }
    
    fun getPostImage(id: Int): ByteArray? {
        val row = Posts.select(Posts.image).where(Posts.id eq id).singleOrNull() ?: return null
        return row[Posts.image]
    }
}
