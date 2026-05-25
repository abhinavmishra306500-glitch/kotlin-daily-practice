import kotlin.random.Random

data class Task(
    val id: Int,
    val title: String,
    var completed: Boolean = false
)

class TaskManager {
    private val tasks = mutableListOf<Task>()

    fun addTask(title: String) {
        val task = Task(Random.nextInt(1000, 9999), title)
        tasks.add(task)
        println("Added: $task")
    }

    fun completeTask(id: Int) {
        tasks.find { it.id == id }?.let {
            it.completed = true
            println("Completed: ${it.title}")
        } ?: println("Task not found")
    }

    fun showTasks() {
        println("\n--- Task List ---")
        tasks.forEach {
            println(
                "[${if (it.completed) "✓" else " "}] " +
                "${it.id} -> ${it.title}"
            )
        }
    }
}

fun main() {
    val manager = TaskManager()

    manager.addTask("Learn Kotlin")
    manager.addTask("Build Android App")
    manager.addTask("Push project to GitHub")

    manager.showTasks()

    println("\nMarking first task complete...\n")
    manager.completeTask( manager.run { Random.nextInt(tasksSize()) } )
}

// Extension function for randomness demo
fun TaskManager.tasksSize(): Int = 1000
