package net.cherrycave.birgid

import io.ktor.websocket.*
import net.cherrycave.birgid.utils.Internal
import net.cherrycave.birgid.utils.implementation

@OptIn(Internal::class)
public suspend fun GertrudClient.closeConnection() {
    implementation.outgoing.send(Frame.Close(CloseReason(CloseReason.Codes.NORMAL, "Disconnected")))
}