package net.cherrycave.birgid.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import net.cherrycave.birgid.utils.SerializableUUID

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("messageType")
public sealed class MessagePayload

@Serializable
@SerialName("empty")
public object Empty : MessagePayload()

@Serializable
@SerialName("ok")
public object GenericOk : MessagePayload()

@Serializable
@SerialName("error")
public data class GenericError(
    val message: String
) : MessagePayload()


@Serializable
@SerialName("send-request")
public data class SendRequest(
    val players: List<SerializableUUID>,
    val server: String
) : MessagePayload()
