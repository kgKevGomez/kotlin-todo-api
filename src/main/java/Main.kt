/**
 * Created by kevin on 06-13-17.
 */

import spark.Spark.*
import com.fasterxml.jackson.module.kotlin.*
import java.lang.Integer.parseInt
import spark.Spark.exception



fun main(args: Array<String>) {
    val port = System.getenv("PORT") ?: "8000"
    port(parseInt(port))

    notFound { _, res ->
        res.type("application/json")
        """{"message":"Sorry, the requested endpoint doesn't exist."}"""
    }

    internalServerError { _, res ->
        res.type("application/json")
        "{\"message\":\"Custom 500 handling\"}"
    }

    exception(IllegalArgumentException::class.java) { ex, _, response ->
        response.status(406)
        response.body("{\"message\":\"${ex.message}\"}")
    }

    after("*"){
        _, res ->
        res.type("application/json")


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
            post("") {
                req, res ->
                    val data : Task = jacksonObjectMapper().readValue(req.body())
                    val name = data.name ?: throw IllegalArgumentException("Name is a required field.")

                    val newTask: Task = taskDao.add(name, data.description ?: "", data.imageUrl ?: "")
                    res.status(201)
                    jacksonObjectMapper().writeValueAsString(newTask)

            }
            put("/:id/status") {
                req, res ->
                if (taskDao.toggleStatus(parseInt(req.params(":id"))))
                    res.status(204)
            }
            put("/:id") {
                req, res ->
                val body = req.body()
                if (body == "" || body == null)
                    throw IllegalArgumentException("The body of the request is required")

                val data : Task = jacksonObjectMapper().readValue(body)

                taskDao.update(parseInt(req.params(":id")), data)
                res.status(204)
            }
        }
    }


}