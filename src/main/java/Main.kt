/**
 * Created by kevin on 06-13-17.
 */

import spark.Spark.*
import com.fasterxml.jackson.module.kotlin.*
import java.lang.Integer.parseInt

fun main(args: Array<String>) {
    port(80)

    // Using string/html
    notFound("<html><body><h1>Custom 404 handling</h1></body></html>")

    val taskDao = TaskDao()
    path("/api/v1.0") {
        path("/tasks") {
            get("") {
                req, res ->
                jacksonObjectMapper()
                    .writeValueAsString(taskDao.tasks)
            }
        }
    }


}