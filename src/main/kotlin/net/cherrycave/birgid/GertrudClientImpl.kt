package net.cherrycave.birgid

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toSet
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.cherrycave.birgid.model.BaseMessage
import net.cherrycave.birgid.standby.Standby
import net.cherrycave.birgid.utils.Internal

@Internal
public class GertrudClientImpl internal constructor(
    public override val host: String,
    public override val port: Int,
    public override val apiKey: String,
    @PublishedApi
    internal val httpClient: HttpClient
) : GertrudClient {
    public val standby: Standby = Standby()

    internal val sendRequests = Channel<BaseMessage>()

    internal val outgoing = Channel<Frame>()

    internal suspend fun queueMessage(message: BaseMessage) {
        outgoing.send(Frame.Text(Json.encodeToString(message)))
    }
}