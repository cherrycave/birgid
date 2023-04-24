package net.cherrycave.birgid.command

import net.cherrycave.birgid.GertrudClient
import net.cherrycave.birgid.model.*
import net.cherrycave.birgid.utils.Internal
import net.cherrycave.birgid.utils.SerializableUUID
import net.cherrycave.birgid.utils.implementation
import net.cherrycave.birgid.utils.nanoid

@OptIn(Internal::class)
public suspend fun GertrudClient.sendRequest(players: List<SerializableUUID>, server: String): Result<Unit> {
    val id = nanoid();

    implementation.queueMessage(BaseMessage(
        messageId = id,
        MessageType.INIT,
        SendRequest(players, server)
    ))

    val message = implementation.standby.waitFor(id)

    return when (message.payload) {
        is GenericOk -> {
            Result.success(Unit)
        }
        is GenericError -> {
            Result.failure(RuntimeException(message.payload.message))
        }
        else -> {
            Result.failure(RuntimeException("Failed to send send request"))
        }
    }
}