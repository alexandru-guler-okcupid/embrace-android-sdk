package io.embrace.android.embracesdk.testcases.features

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.embrace.android.embracesdk.testframework.actions.EmbraceSetupInterface
import io.embrace.android.embracesdk.testframework.IntegrationTestRule
import io.embrace.android.embracesdk.internal.config.behavior.REDACTED_LABEL
import io.embrace.android.embracesdk.internal.config.behavior.SensitiveKeysBehaviorImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class SensitiveKeysRedactionFeatureTest {
    @Rule
    @JvmField
    val testRule: IntegrationTestRule = IntegrationTestRule {
        EmbraceSetupInterface(startImmediately = false)
    }

    private val sensitiveKeysBehavior = SensitiveKeysBehaviorImpl(
        listOf("password")
    )

    @Test
    fun `custom span properties are redacted if they are sensitive`() {
        testRule.runTest(
            setupAction = {
                overriddenConfigService.sensitiveKeysBehavior = sensitiveKeysBehavior
            },
            testCaseAction = {
                startSdk()
                recordSession {
                    val span = embrace.startSpan("test span")
                    span?.addAttribute("password", "1234")
                    span?.addAttribute("not a password", "1234")
                    span?.stop()
                }
            },
            assertAction = {
                val session = getSingleSessionEnvelope()
                val recordedSpan = session.data.spans?.find { it.name == "test span" }
                val sensitiveAttribute = recordedSpan?.attributes?.first { it.key == "password" }
                val notSensitiveAttribute = recordedSpan?.attributes?.first { it.key == "not a password" }

                assertEquals(REDACTED_LABEL, sensitiveAttribute?.data)
                assertEquals("1234", notSensitiveAttribute?.data)
            }
        )
    }

    @Test
    fun `custom span events are redacted if they are sensitive`() {
        testRule.runTest(
            setupAction = {
                overriddenConfigService.sensitiveKeysBehavior = sensitiveKeysBehavior
            },
            testCaseAction = {
                startSdk()
                recordSession {
                    val span = embrace.startSpan("test span")
                    span?.addEvent("event", null, mapOf("password" to "123456", "status" to "ok"))
                    span?.addEvent("anotherEvent", null, mapOf("password" to "654321", "someKey" to "someValue"))
                    span?.stop()
                }
            },
            assertAction = {
                val session = getSingleSessionEnvelope()
                val recordedSpan = session.data.spans?.find { it.name == "test span" }

                val event = recordedSpan?.events?.first { it.name == "event" }
                val anotherEvent = recordedSpan?.events?.first { it.name == "anotherEvent" }
                assertTrue(event?.attributes?.any { it.key == "password" && it.data == REDACTED_LABEL } ?: false)
                assertTrue(event?.attributes?.any { it.key == "status" && it.data == "ok" } ?: false)
                assertTrue(anotherEvent?.attributes?.any { it.key == "password" && it.data == REDACTED_LABEL } ?: false)
                assertTrue(anotherEvent?.attributes?.any { it.key == "someKey" && it.data == "someValue" } ?: false)
            }
        )
    }
}