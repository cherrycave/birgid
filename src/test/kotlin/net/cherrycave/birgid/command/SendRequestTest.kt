package net.cherrycave.birgid.command

import kotlinx.coroutines.*
import net.cherrycave.birgid.GertrudClient
import net.cherrycave.birgid.connect
import java.util.*
import kotlin.test.Test

class SendRequestTest {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @Test
    fun testSendRequest() = runBlocking {
        val gertrudClient = GertrudClient {
            host = "cherrycave-backend.cap.stckoverflw.net"
            port = 80
            identifier = "test"
            apiKey = "UKFo@BYKWsG#rcnYzyW^jBVkP53&etky7zan*vMr6A"
        }

        val connection = coroutineScope.launch {
            gertrudClient.connect()
        }

        val uuid = UUID.randomUUID()
        val server = "test"

        gertrudClient.sendRequest(listOf(uuid), server)

        delay(60000)

        connection.cancel()
    }
}