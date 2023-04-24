package net.cherrycave.birgid

/**
 * Represents the root implementation of the Cherry Cave Backend Client.
 *
 * Please use the [GertrudClient] function to create an instance
 *
 * @property host the host of the Cherry Cave Backend
 */
public sealed interface GertrudClient {

    public val host: String
    public val port: Int

    public val apiKey: String

}
