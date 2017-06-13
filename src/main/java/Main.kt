/**
 * Created by kevin on 06-13-17.
 */

import spark.Spark.*
import com.fasterxml.jackson.module.kotlin.*
import java.lang.Integer.parseInt

fun main(args: Array<String>) {
    port(parseInt(System.getenv("PORT")))

    // Using string/html
    notFound("<html><body><h1>Custom 404 handling</h1></body></html>")

    val taskDao = TaskDao()
    path("/api/v1.0") {
        before("/*") {
            request, response -> response.header("Content-Type", "application/json")
        }
        path("/tasks") {
            get("") {
                req, res ->
                jacksonObjectMapper()
                    .writeValueAsString(taskDao.tasks)
            }
            put("/:id") {
                req, res ->
                if (taskDao.complete(parseInt(req.params(":id"))))
                    res.status(204)
            }
        }
    }


}