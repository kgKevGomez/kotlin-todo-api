import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by kevin on 06-13-17.
 */

class TaskDao {
    val tasks = mutableListOf(
            Task(1, "Learn Kotlin", "Kotlin is awesome!", false),
            Task(2, "Create a 'Hello World' App", "Using spark framework", false)
    )

    var lastId: AtomicInteger = AtomicInteger(tasks.size)

    fun add(name: String, description: String?) : Task {
        if (tasks.any { x -> x.name?.trim()?.toLowerCase().equals(name) })
            throw IllegalArgumentException("Another task with the same name already exists.")

        val id = lastId.incrementAndGet()
        val newTask = Task(id, name, description, false)
        tasks.add(newTask)

        return newTask
    }

    fun toggleStatus(id: Int) : Boolean {
        val task: Task = tasks.find { (id1) -> id1 == id } ?: throw IllegalArgumentException("The provided Task ID doesn't exist")
        task.done = !(task.done?:true)

        return true
    }
}