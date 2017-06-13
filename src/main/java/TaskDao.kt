import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by kevin on 06-13-17.
 */

class TaskDao {
    val tasks = hashMapOf(
            0 to Task("Learn Kotlin", "Kotlin is awesome!", false),
            1 to Task("Create a 'Hello World' App", "Using spark framework", false)
    )

    var lastId: AtomicInteger = AtomicInteger(tasks.size - 1)

    fun save(name: String, description: String, done: Boolean) {
        val id = lastId.incrementAndGet()
        tasks.put(id, Task(name, description, done))
    }


}