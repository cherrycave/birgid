package net.cherrycave.birgid

import io.github.oshai.KotlinLogging
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.cherrycave.birgid.model.*
import net.cherrycave.birgid.utils.Internal
import net.cherrycave.birgid.utils.implementation
import kotlin.time.Duration.Companion.seconds

private val LOG = KotlinLogging.logger { }

@OptIn(Internal::class, ExperimentalCoroutinesApi::class)
public suspend fun GertrudClient.connect() {
    var retries = 0
    var disconnect = false

    while (!disconnect) {
        implementation.httpClient.webSocket(host = host, port = port, path = "/ws") {
            var lastKeepAlive = System.currentTimeMillis()

            launch {
                while (this.isActive) {
                    delay(3.seconds)

                    if (System.currentTimeMillis() - lastKeepAlive > 6.seconds.inWholeMilliseconds) {
                        close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "Keep Alive Timeout"))
                    }
                }
            }

            launch {
                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            val message: BaseMessage = Json.decodeFromString(frame.readText())

                            when (message.messageType) {
                                MessageType.INIT -> {
                                    when (message.payload) {
                                        is SendRequest -> {
                                            implementation.sendRequests.send(message)
                                        }

                                        is GenericError -> {}
                                        GenericOk -> {}
                                    }
                                }

                                MessageType.RESPONSE -> {
                                    implementation.standby.process(message)
                                }

                                MessageType.KEEP_ALIVE -> {
                                    lastKeepAlive = System.currentTimeMillis()
                                    implementation.queueMessage(message)
                                }
                            }
                        }

                        is Frame.Close -> {
                            close()
                        }

                        else -> {
                            // Ignore
                        }
                    }
                }
            }

            while (this.outgoing.isClosedForSend.not()) {
                val message = implementation.outgoing.receive()

                outgoing.send(message)
                if (message is Frame.Close) {
                    LOG.info { "Disconnected from CherryCave Backend" }
                    disconnect = true
                    break
                }
            }

        }
        if (!disconnect) {
            if (retries >= 5) {
                throw RuntimeException("Could not reconnect to CherryCave Backend")
            }

            retries++

            LOG.info { "Lost connection. Reconnecting to CherryCave Backend in 5 seconds" }
            delay(5.seconds)
        }

    }
}