import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val createdAt: String
)

object NoteApp {

    private val notes = mutableListOf<Note>()

    private fun generateId(): Int {
        return Random.nextInt(1000, 9999)
    }

    fun addNote(title: String, content: String) {
        val time = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        val note = Note(
            id = generateId(),
            title = title,
            content = content,
            createdAt = time
        )

        notes.add(note)
        println("✅ Note added: ${note.title}")
    }

    fun listNotes() {
        if (notes.isEmpty()) {
            println("No notes found.")
            return
        }

        println("\n===== NOTES =====")
        notes.forEach {
            println("""
ID: ${it.id}
Title: ${it.title}
Content: ${it.content}
Created: ${it.createdAt}
-------------------------
""".trimIndent())
        }
    }

    fun saveToFile(path: String) {
        val file = File(path)

        val content = buildString {
            notes.forEach {
                appendLine("${it.id}|${it.title}|${it.content}|${it.createdAt}")
            }
        }

        file.writeText(content)
        println("💾 Notes saved to $path")
    }

    fun loadFromFile(path: String) {
        val file = File(path)

        if (!file.exists()) {
            println("File not found.")
            return
        }

        notes.clear()

        file.readLines().forEach { line ->
            val parts = line.split("|")

            if (parts.size == 4) {
                notes.add(
                    Note(
                        id = parts[0].toInt(),
                        title = parts[1],
                        content = parts[2],
                        createdAt = parts[3]
                    )
                )
            }
        }

        println("📂 Notes loaded from $path")
    }
}

fun main() {

    NoteApp.addNote(
        title = "Learn Kotlin",
        content = "Practice extension functions and coroutines."
    )

    NoteApp.addNote(
        title = "Project Idea",
        content = "Build a terminal-based password manager."
    )

    NoteApp.listNotes()

    val path = "notes.txt"

    NoteApp.saveToFile(path)

    println("\nReloading from file...\n")

    NoteApp.loadFromFile(path)

    NoteApp.listNotes()
}
