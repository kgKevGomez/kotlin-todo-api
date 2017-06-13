/**
 * Created by kevin on 06-13-17.
 */

import spark.Spark.*
import com.fasterxml.jackson.module.kotlin.*
import java.lang.Integer.parseInt

fun main(args: Array<String>) {
    port(parseInt(System.getenv("PORT")))

    notFound { req, res ->
        res.type("application/json")
        """{"message":"Custom 404"}"""
    }

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