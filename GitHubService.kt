package github.demo

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Simple GitHub API Client using Kotlin + Ktor
 * Fetches public profile information.
 */

@Serializable
data class GitHubUser(
    val login: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int
)

class GitHubService {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    suspend fun fetchUser(username: String): GitHubUser {
        return client.get("https://api.github.com/users/$username").body()
    }

    fun close() {
        client.close()
    }
}

fun main() = runBlocking {

    val service = GitHubService()

    try {
        val user = service.fetchUser("octocat")

        println("===== GitHub Profile =====")
        println("Username      : ${user.login}")
        println("Followers     : ${user.followers}")
        println("Following     : ${user.following}")
        println("Public Repos  : ${user.public_repos}")

    } catch (e: Exception) {
        println("Failed to fetch user data.")
        println("Reason: ${e.message}")

    } finally {
        service.close()
    }
}
