package net.cherrycave.birgid.command

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.cherrycave.birgid.GertrudClient
import net.cherrycave.birgid.utils.Internal
import net.cherrycave.birgid.utils.implementation

@OptIn(Internal::class)
public suspend fun GertrudClient.registerServer(register: Boolean, serverType: ServerType): Result<Unit> {
    val response = implementation.httpClient.post(implementation.buildUrl() + "/commands/servers/registrations") {
        setBody(RegisterServerRequest(register, serverType))
    }

    return if (response.status.isSuccess()) {
        Result.success(Unit)
    } else {
        Result.failure(Exception("Failed to register server: ${response.body<String>()}"))
    }
}

@Serializable
internal data class RegisterServerRequest(
    val register: Boolean,
    val serverType: ServerType
)

@Serializable
public enum class ServerType {
    @SerialName("lobby")
    LOBBY,
    @SerialName("other")
    OTHER
}
