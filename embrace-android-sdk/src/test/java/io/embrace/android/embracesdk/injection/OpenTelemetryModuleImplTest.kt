package io.embrace.android.embracesdk.injection

import io.embrace.android.embracesdk.arch.destination.LogWriterImpl
import io.embrace.android.embracesdk.internal.logs.LogSinkImpl
import io.embrace.android.embracesdk.internal.spans.CurrentSessionSpanImpl
import io.embrace.android.embracesdk.internal.spans.EmbraceSpanService
import io.embrace.android.embracesdk.internal.spans.SpanSinkImpl
import io.opentelemetry.api.logs.Logger
import org.junit.Assert.assertTrue
import org.junit.Test

internal class OpenTelemetryModuleImplTest {
    @Test
    fun testInitModuleImplDefaults() {
        val openTelemetryModule = OpenTelemetryModuleImpl(InitModuleImpl())
        assertTrue(openTelemetryModule.spanSink is SpanSinkImpl)
        assertTrue(openTelemetryModule.spanService is EmbraceSpanService)
        assertTrue(openTelemetryModule.currentSessionSpan is CurrentSessionSpanImpl)
        assertTrue(openTelemetryModule.logWriter is LogWriterImpl)
        assertTrue(openTelemetryModule.logSink is LogSinkImpl)
        assertTrue(openTelemetryModule.logger is Logger)
    }
}
