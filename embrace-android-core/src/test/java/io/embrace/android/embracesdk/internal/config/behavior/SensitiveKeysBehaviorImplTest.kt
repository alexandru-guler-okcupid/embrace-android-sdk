package io.embrace.android.embracesdk.internal.config.behavior

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class SensitiveKeysBehaviorImplTest {

    @Test
    fun `keys are not sensitive if they are not in the sensitive keys list`() {
        // given an empty sensitive list
        val behavior = SensitiveKeysBehaviorImpl(emptyList())

        // when checking if a key is sensitive
        val isSensitive = behavior.isSensitiveKey("password")

        // then the keys are not sensitive
        assertFalse(isSensitive)
    }

    @Test
    fun `keys are not sensitive with a null sensitive keys list`() {
        // given a null sensitive list
        val behavior = SensitiveKeysBehaviorImpl(null)

        // when checking if a key is sensitive
        val isSensitive = behavior.isSensitiveKey("password")

        // then the keys are not sensitive
        assertFalse(isSensitive)
    }

    @Test
    fun `keys are sensitive when found in the sensitive keys list`() {
        // given a sensitive list with a key
        val behavior = SensitiveKeysBehaviorImpl(listOf("password"))

        // when checking if a key present in the list is sensitive
        val isSensitive = behavior.isSensitiveKey("password")

        // then the key is sensitive
        assertTrue(isSensitive)
    }

    @Test
    fun `keys in the sensitive list are truncated to 128 characters`() {
        // given a sensitive list with a long key
        val behavior = SensitiveKeysBehaviorImpl(listOf("a".repeat(200)))

        // when checking if a key present in the list is sensitive
        val sensitiveKey = behavior.isSensitiveKey("a".repeat(128))
        val notSensitiveKey = behavior.isSensitiveKey("a".repeat(129))

        // then the key is sensitive if it has 128 characters
        assertTrue(sensitiveKey)
        assertFalse(notSensitiveKey)
    }

    @Test
    fun `sensitive list is truncated to 10000 keys`() {
        // given a sensitive list with more than 10000 keys
        val behavior = SensitiveKeysBehaviorImpl(
            List(10000) { it.toString() } + "password"
        )

        // when checking a key present in the 10001st position
        val sensitiveKey = behavior.isSensitiveKey("password")

        // then the key is not sensitive
        assertFalse(sensitiveKey)
    }

    @Test
    fun `sensitive list with multiple keys`() {
        // given a sensitive list with multiple keys
        val behavior = SensitiveKeysBehaviorImpl(
            listOf(
                "password",
                "passkey"
            )
        )

        // when checking if a key present in the list is sensitive
        val sensitiveKey = behavior.isSensitiveKey("password")
        val anotherSensitiveKey = behavior.isSensitiveKey("passkey")
        val notSensitiveKey = behavior.isSensitiveKey("username")

        // then the sensitivity is returned accordingly
        assertTrue(sensitiveKey)
        assertTrue(anotherSensitiveKey)
        assertFalse(notSensitiveKey)
    }
}
