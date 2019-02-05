package com.example.kotlinserver

import io.ktor.server.netty.*
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.server.engine.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.*

val port = System.getenv("PORT")?.toInt() ?: 23567

fun main(args: Array<String>) {
    embeddedServer(Netty, port) {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }
        routing {
            get("") {
                call.respond("I'm alive!")
            }
            get("hello") {
                call.respond(HttpStatusCode.Accepted, "Hello")
            }
            get("random/{min}/{max}") {
                val min = call.parameters["min"]?.toIntOrNull() ?: 0
                val max = call.parameters["max"]?.toIntOrNull() ?: 10
                val randomString = "${(min until max).shuffled().last()}"
                call.respond(mapOf("value" to randomString))
            }
        }
    }.start(wait = true)
}