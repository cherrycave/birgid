package net.cherrycave.birgid.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class BaseMessage(
    val messageId: String,
    val messageType: MessageType,
    val payload: MessagePayload,
)

@Serializable
public enum class MessageType {
    @SerialName("init")
    INIT,

    @SerialName("response")
    RESPONSE,

    @SerialName("keepAlive")
    KEEP_ALIVE,
}
