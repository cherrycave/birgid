package net.cherrycave.birgid

import io.ktor.client.*
import net.cherrycave.birgid.utils.Internal

@Internal
public class GertrudClientImpl internal constructor(
    public override val host: String,
    public override val port: Int,
    public override val apiKey: String,
    public override val https: Boolean,
    @PublishedApi
    internal val httpClient: HttpClient
) : GertrudClient {
    public fun buildUrl(): String {
        return if (https) {
            "https://$host:$port"
        } else {
            "http://$host:$port"
        }
    }
}