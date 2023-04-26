package net.cherrycave.birgid.receiver

import kotlinx.coroutines.channels.consumeEach
import net.cherrycave.birgid.GertrudClient
import net.cherrycave.birgid.model.BaseMessage
import net.cherrycave.birgid.model.GenericError
import net.cherrycave.birgid.model.GenericOk
import net.cherrycave.birgid.model.MessageType
import net.cherrycave.birgid.utils.Internal
import net.cherrycave.birgid.utils.implementation

@OptIn(Internal::class)
/**
 * Handle send requests. the [BaseMessage] that the consumer receives will always have the payload of [net.cherrycave.birgid.model.SendRequest]
 */
public suspend fun GertrudClient.handleSendRequest(handler: (message: BaseMessage) -> Result<Unit>) {
    implementation.sendRequests.consumeEach {
        val result = handler(it)
        if (result.isSuccess) {
            implementation.queueMessage(
                BaseMessage(
                    messageId = it.messageId,
                    MessageType.RESPONSE,
                    GenericOk
                )
            )
        } else {
            implementation.queueMessage(
                BaseMessage(
                    messageId = it.messageId,
                    MessageType.RESPONSE,
                    GenericError(result.exceptionOrNull()?.message ?: "Unknown error")
                )
            )
        }
    }
}