package net.cherrycave.birgid.command

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import net.cherrycave.birgid.GertrudClient
import net.cherrycave.birgid.utils.Internal
import net.cherrycave.birgid.utils.implementation

public interface Settings

@OptIn(Internal::class)
public suspend fun GertrudClient.postSettings(
    id: String,
    settings: Settings
): Result<Unit> {
    val response = implementation.httpClient.post(implementation.buildUrl() + "/commands/servers/settings/$id") {
        setBody(settings)
    }

    return if (response.status.isSuccess()) {
        Result.success(Unit)
    } else {
        Result.failure(Exception("Failed to post settings: ${response.body<String>()}"))
    }
}
