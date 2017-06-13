/**
 * Created by kevin on 06-13-17.
 */

import spark.Spark.*
import com.fasterxml.jackson.module.kotlin.*
fun main(args: Array<String>) {
    port(8080)

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