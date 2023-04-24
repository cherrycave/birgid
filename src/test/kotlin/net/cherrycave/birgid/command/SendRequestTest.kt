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
            identifier = "proxy"
            host = "localhost"
            port = 6969
            apiKey = "abc"
        }

        val connection = coroutineScope.launch {
            gertrudClient.connect()
        }

        val uuid = UUID.randomUUID()
        val server = "test"

        println("adawdfaw")
        val result = gertrudClient.sendRequest(listOf(uuid), server)

        assert(result.isSuccess)

        connection.cancel()
    }
}