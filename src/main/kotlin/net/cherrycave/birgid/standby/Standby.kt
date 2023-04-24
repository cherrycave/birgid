package net.cherrycave.birgid.standby

import kotlinx.coroutines.channels.Channel
import net.cherrycave.birgid.model.BaseMessage

public class Standby {

    private val bystanders = mutableMapOf<String, Channel<BaseMessage>>()

    public suspend fun waitFor(messageId: String): BaseMessage {
        val channel = Channel<BaseMessage>()
        bystanders[messageId] = channel
        val message = channel.receive()
        bystanders.remove(messageId)?.close()
        return message
    }

    public suspend fun process(message: BaseMessage) {
        val channel = bystanders[message.messageId]
        channel?.send(message)
    }

}