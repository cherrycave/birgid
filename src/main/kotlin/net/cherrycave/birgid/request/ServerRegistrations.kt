package net.cherrycave.birgid.request

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import net.cherrycave.birgid.GertrudClient
import net.cherrycave.birgid.command.ServerType
import net.cherrycave.birgid.utils.Internal
import net.cherrycave.birgid.utils.implementation

public data class ServerRegistration(
    val register: Boolean,
    val identifier: String,
    val serverType: ServerType,
    val host: String,
    val port: Int,
)

@OptIn(Internal::class)
public suspend fun GertrudClient.getServerRegistrations(): Result<List<ServerRegistration>> {
    val registrations = implementation.httpClient.get(implementation.buildUrl() + "/commands/servers/registrations")

    return if (registrations.status.isSuccess()) {
        Result.success(registrations.body())
    } else {
        Result.failure(Exception("Failed to get server registrations: ${registrations.body<String>()}"))
    }
}