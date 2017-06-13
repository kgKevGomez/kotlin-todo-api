import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by kevin on 06-13-17.
 */

class TaskDao {
    val tasks = mutableListOf(
            Task(1, "Learn Kotlin", "Kotlin is awesome!", false),
            Task(2, "Create a 'Hello World' App", "Using spark framework", false)
    )

    var lastId: AtomicInteger = AtomicInteger(tasks.size - 1)

    fun add(name: String, description: String, done: Boolean) {
        val id = lastId.incrementAndGet()
        tasks.add(Task(id, name, description, done))
    }

    fun complete(id: Int) : Boolean {
        val task: Task = tasks.find { (id1) -> id1 == id } ?: throw IllegalArgumentException("The provided Task ID doesn't exist")
        task.done = true

        return true
    }


}