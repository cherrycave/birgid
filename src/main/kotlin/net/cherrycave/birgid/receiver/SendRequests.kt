package net.cherrycave.birgid.receiver

import kotlinx.coroutines.channels.consumeEach
import net.cherrycave.birgid.GertrudClient
import net.cherrycave.birgid.model.BaseMessage
import net.cherrycave.birgid.utils.Internal
import net.cherrycave.birgid.utils.implementation
import java.util.function.Consumer

@OptIn(Internal::class)
/**
 * Handle send requests. the [BaseMessage] that the consumer receives will always have the payload of [net.cherrycave.birgid.model.SendRequest]
 */
public suspend fun GertrudClient.handleSendRequest(handler: Consumer<BaseMessage>) {
    implementation.sendRequests.consumeEach {
        handler.accept(it)
    }
}