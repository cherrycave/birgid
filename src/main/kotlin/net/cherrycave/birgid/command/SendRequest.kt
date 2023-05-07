package net.cherrycave.birgid.command

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import net.cherrycave.birgid.GertrudClient
import net.cherrycave.birgid.model.SendRequest
import net.cherrycave.birgid.utils.Internal
import net.cherrycave.birgid.utils.SerializableUUID
import net.cherrycave.birgid.utils.implementation

@OptIn(Internal::class)
public suspend fun GertrudClient.sendRequest(players: List<SerializableUUID>, server: String): Result<Unit> {
    val result = implementation.httpClient.post(implementation.buildUrl() + "/commands/servers/sendRequest") {
        setBody(SendRequest(players, server))
    }

    return if (result.status.isSuccess()) {
        Result.success(Unit)
    } else {
        Result.failure(Exception("Failed to send request: ${result.body<String>()}"))
    }
}