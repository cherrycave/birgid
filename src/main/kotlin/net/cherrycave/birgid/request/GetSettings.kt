package net.cherrycave.birgid.request

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import net.cherrycave.birgid.GertrudClient
import net.cherrycave.birgid.command.Settings
import net.cherrycave.birgid.utils.Internal
import net.cherrycave.birgid.utils.implementation

@OptIn(Internal::class)
public suspend fun GertrudClient.getSettings(id: String): Result<Settings> {
    val result = implementation.httpClient.get(implementation.buildUrl() + "/commands/servers/settings/$id")

    return if (result.status.isSuccess()) {
        Result.success(result.body())
    } else {
        Result.failure(Exception("Failed to get settings: ${result.body<String>()}"))
    }
}