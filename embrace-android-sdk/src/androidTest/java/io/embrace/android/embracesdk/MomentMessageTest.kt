package io.embrace.android.embracesdk

import com.google.gson.stream.JsonReader
import io.embrace.android.embracesdk.comms.delivery.DeliveryFailedApiCalls
import io.embrace.android.embracesdk.internal.EmbraceSerializer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.IOException

private const val MOMENT_NAME = "my_moment"

internal class MomentMessageTest : BaseTest() {

    @Before
    fun setup() {
        startEmbraceInForeground()
    }

    @After
    fun tearDown() {
        sendBackground()
    }

    /**
     * Verifies that a custom moment is sent by the SDK.
     */
    @Test
    fun customMomentTest() {
        // Send start moment
        Embrace.getInstance().startMoment(MOMENT_NAME)

        // Validate start moment request
        waitForRequest { request ->
            validateMessageAgainstGoldenFile(request, "moment-custom-start-event.json")
        }

        // Send end moment
        Embrace.getInstance().endMoment(MOMENT_NAME)

        // Validate end moment request
        waitForRequest { request ->
            validateMessageAgainstGoldenFile(request, "moment-custom-end-event.json")
        }
    }

    /**
     * Verifies that a custom moment with properties is sent by the SDK.
     */
    @Test
    fun startMomentWithPropertiesTest() {
        // ignore startup event
        Embrace.getImpl().endAppStartup(null)
        waitForRequest()

        val properties = HashMap<String, Any>()
        properties["key1"] = "value1"
        properties["key2"] = "value2"

        // Send start moment with properties
        Embrace.getInstance().startMoment(MOMENT_NAME, MOMENT_NAME, properties)

        // Validate start moment request with properties
        waitForRequest { request ->
            validateMessageAgainstGoldenFile(
                request,
                "moment-custom-with-properties-start-event.json"
            )
        }

        // Send end moment
        Embrace.getInstance().endMoment(MOMENT_NAME)

        // Validate end moment request
        waitForRequest { request ->
            validateMessageAgainstGoldenFile(
                request,
                "moment-custom-with-properties-end-event.json"
            )
        }

    }

    /**
     * Verifies that a custom moment is sent by the SDK.
     */
    @Test
    fun customMomentFailRequestTest() {
        waitForFailedRequest(
            endpoint = EmbraceEndpoint.EVENTS,
            request = { Embrace.getInstance().startMoment(MOMENT_NAME) },
            action = {
                // Validate start moment request
                waitForRequest { request ->
                    validateMessageAgainstGoldenFile(request, "moment-custom-start-event.json")
                }
            },
            validate = { file -> validateFileContent(file) })
    }

    private fun validateFileContent(file: File) {
        try {
            assertTrue(file.exists() && !file.isDirectory)
            readFile(file, EmbraceEndpoint.EVENTS.url)
            val serializer = EmbraceSerializer()
            file.bufferedReader().use { bufferedReader ->
                JsonReader(bufferedReader).use { jsonreader ->
                    jsonreader.isLenient = true
                    val obj = serializer.loadObject(jsonreader, DeliveryFailedApiCalls::class.java)
                    if (obj != null) {
                        val failedCallFileName = obj.element().cachedPayloadFilename
                        assert(failedCallFileName.isNotBlank())
                        readFileContent("\"t\":\"start\"", failedCallFileName)
                    } else {
                        fail("Null object")
                    }
                }
            }
        } catch (e: IOException) {
            fail("IOException error: ${e.message}")
        }
    }
}
