package net.cherrycave.birgid

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json
import net.cherrycave.birgid.utils.Internal
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Creates a new [GertrudClient] by applying [builder].
 */
@OptIn(ExperimentalContracts::class)
public inline fun GertrudClient(builder: GertrudClientBuilder.() -> Unit = {}): GertrudClient {
    contract {
        callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
    }

    return GertrudClientBuilder().apply(builder).build()
}

/**
 * Builder for all options for a [GertrudClient]
 */
public class GertrudClientBuilder {
    /**
     * The Host of the Cherrycave Backend
     */
    public lateinit var host: String

    /**
     * The port of the Cherrycave Backend
     */
    public var port: Int = 443

    /**
     * The Identifier for the type of connection
     */
    public lateinit var identifier: String

    /**
     * The API key to use for authentication
     */
    public lateinit var apiKey: String

    /**
     * Creates a new [GertrudClient] with the specified options.
     */
    @OptIn(Internal::class)
    public fun build(): GertrudClient = GertrudClientImpl(host, port, apiKey, HttpClient {
        install(Logging) {
            this.level = LogLevel.ALL
        }
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
        defaultRequest {
            header(HttpHeaders.Authorization, apiKey)
            header("X-Server-Identifier", identifier)
        }
    })
}
