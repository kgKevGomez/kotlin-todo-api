/**
 * Created by kevin on 06-13-17.
 */

import spark.Spark.*
import com.fasterxml.jackson.module.kotlin.*
import java.lang.Integer.parseInt
import spark.Spark.exception



fun main(args: Array<String>) {
    port(parseInt(System.getenv("PORT")))

    notFound { _, res ->
        res.type("application/json")
        """{"message":"Custom 404"}"""
    }

    internalServerError { _, res ->
        res.type("application/json")
        "{\"message\":\"Custom 500 handling\"}"
    }

    exception(IllegalArgumentException::class.java) { _, _, response ->
        response.status(404)
        response.body("{\"message\":\"The requested task doesn't exist\"}")
    }

    val taskDao = TaskDao()
    path("/api/v1.0") {
        before("/*") {
            _, response -> response.header("Content-Type", "application/json")
        }
        path("/tasks") {
            get("") {
                _, _ ->
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