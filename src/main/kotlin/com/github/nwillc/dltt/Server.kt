/*
 * Copyright 2019 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.dltt

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.nwillc.dltt.model.Policy
import com.github.nwillc.dltt.model.PolicyEvent
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.LoggerFactory

private val LOGGER = LoggerFactory.getLogger(Server::class.java)

private const val DEFAULT_PORT = 8080

class Server : CliktCommand() {
    val policies = mutableMapOf<String,Policy>()
    val port by option(help = "Port number to run server on.").int().default(DEFAULT_PORT)

    override fun run() {
        LOGGER.info("Starting on {}", port)
        val server = embeddedServer(Netty, port) {
            routing {
                get("ping") {
                    LOGGER.info("ACK")
                    call.respond(HttpStatusCode.Accepted)
                }
                get("policies") {
                    LOGGER.debug("list policies")

                    policies.values.forEach { LOGGER.info(it.toString()) }
                }
                post("policies/{id}") {
                    val id = call.parameters["id"]!!
                    val duration = call.parameters["duration"] ?: "12"

                    LOGGER.debug("Create policy {} with duration {}", id, duration)

                    policies[id] = Policy(id,duration.toInt())
                }
                put("policies/{id}/{event}") {
                    val id = call.parameters["id"]
                    val event = PolicyEvent.valueOf(call.parameters["event"]!!)

                    LOGGER.debug("Event {} for policy {}", event, id)
                    policies[id]?.accept(event)
                }
            }
        }
        server.start(true)
    }
}

fun main(args: Array<String>) = Server().main(args)