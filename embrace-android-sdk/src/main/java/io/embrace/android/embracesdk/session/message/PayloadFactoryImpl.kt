package io.embrace.android.embracesdk.session.message

import io.embrace.android.embracesdk.config.ConfigService
import io.embrace.android.embracesdk.logging.EmbLogger
import io.embrace.android.embracesdk.payload.ApplicationState
import io.embrace.android.embracesdk.payload.LifeEventType
import io.embrace.android.embracesdk.payload.SessionMessage
import io.embrace.android.embracesdk.payload.SessionZygote
import io.embrace.android.embracesdk.session.lifecycle.ProcessState
import io.embrace.android.embracesdk.session.orchestrator.SessionSnapshotType

internal class PayloadFactoryImpl(
    private val payloadMessageCollator: PayloadMessageCollator,
    private val configService: ConfigService,
    private val logger: EmbLogger
) : PayloadFactory {

    override fun startPayloadWithState(state: ProcessState, timestamp: Long, coldStart: Boolean) =
        when (state) {
            ProcessState.FOREGROUND -> startSessionWithState(timestamp, coldStart)
            ProcessState.BACKGROUND -> startBackgroundActivityWithState(timestamp, coldStart)
        }

    override fun endPayloadWithState(state: ProcessState, timestamp: Long, initial: SessionZygote) =
        when (state) {
            ProcessState.FOREGROUND -> endSessionWithState(initial)
            ProcessState.BACKGROUND -> endBackgroundActivityWithState(initial)
        }

    override fun endPayloadWithCrash(
        state: ProcessState,
        timestamp: Long,
        initial: SessionZygote,
        crashId: String
    ) = when (state) {
        ProcessState.FOREGROUND -> endSessionWithCrash(initial, crashId)
        ProcessState.BACKGROUND -> endBackgroundActivityWithCrash(initial, crashId)
    }

    override fun snapshotPayload(state: ProcessState, timestamp: Long, initial: SessionZygote) =
        when (state) {
            ProcessState.FOREGROUND -> snapshotSession(initial)
            ProcessState.BACKGROUND -> snapshotBackgroundActivity(initial)
        }

    override fun startSessionWithManual(timestamp: Long): SessionZygote {
        return payloadMessageCollator.buildInitialSession(
            InitialEnvelopeParams(
                false,
                LifeEventType.MANUAL,
                timestamp,
                ApplicationState.FOREGROUND
            )
        )
    }

    override fun endSessionWithManual(timestamp: Long, initial: SessionZygote): SessionMessage {
        return payloadMessageCollator.buildFinalSessionMessage(
            FinalEnvelopeParams(
                initial = initial,
                endType = SessionSnapshotType.NORMAL_END,
                logger = logger
            )
        )
    }

    private fun startSessionWithState(timestamp: Long, coldStart: Boolean): SessionZygote {
        return payloadMessageCollator.buildInitialSession(
            InitialEnvelopeParams(
                coldStart,
                LifeEventType.STATE,
                timestamp,
                ApplicationState.FOREGROUND
            )
        )
    }

    private fun startBackgroundActivityWithState(timestamp: Long, coldStart: Boolean): SessionZygote? {
        if (!configService.isBackgroundActivityCaptureEnabled()) {
            return null
        }

        // kept for backwards compat. the backend expects the start time to be 1 ms greater
        // than the adjacent session, and manually adjusts.
        val time = when {
            coldStart -> timestamp
            else -> timestamp + 1
        }
        return payloadMessageCollator.buildInitialSession(
            InitialEnvelopeParams(
                coldStart = coldStart,
                startType = LifeEventType.BKGND_STATE,
                startTime = time,
                ApplicationState.BACKGROUND
            )
        )
    }

    private fun endSessionWithState(initial: SessionZygote): SessionMessage {
        return payloadMessageCollator.buildFinalSessionMessage(
            FinalEnvelopeParams(
                initial = initial,
                endType = SessionSnapshotType.NORMAL_END,
                logger = logger
            )
        )
    }

    private fun endBackgroundActivityWithState(initial: SessionZygote): SessionMessage? {
        if (!configService.isBackgroundActivityCaptureEnabled()) {
            return null
        }

        // kept for backwards compat. the backend expects the start time to be 1 ms greater
        // than the adjacent session, and manually adjusts.
        return payloadMessageCollator.buildFinalSessionMessage(
            FinalEnvelopeParams(
                initial = initial,
                endType = SessionSnapshotType.NORMAL_END,
                logger = logger
            )
        )
    }

    private fun endSessionWithCrash(
        initial: SessionZygote,
        crashId: String
    ): SessionMessage {
        return payloadMessageCollator.buildFinalSessionMessage(
            FinalEnvelopeParams(
                initial = initial,
                endType = SessionSnapshotType.JVM_CRASH,
                logger = logger,
                crashId = crashId
            )
        )
    }

    private fun endBackgroundActivityWithCrash(
        initial: SessionZygote,
        crashId: String
    ): SessionMessage? {
        if (!configService.isBackgroundActivityCaptureEnabled()) {
            return null
        }
        return payloadMessageCollator.buildFinalSessionMessage(
            FinalEnvelopeParams(
                initial = initial,
                endType = SessionSnapshotType.JVM_CRASH,
                logger = logger,
                crashId = crashId
            )
        )
    }

    /**
     * Called when the session is persisted every 2s to cache its state.
     */
    private fun snapshotSession(initial: SessionZygote): SessionMessage {
        return payloadMessageCollator.buildFinalSessionMessage(
            FinalEnvelopeParams(
                initial = initial,
                endType = SessionSnapshotType.PERIODIC_CACHE,
                logger = logger
            )
        )
    }

    private fun snapshotBackgroundActivity(initial: SessionZygote): SessionMessage? {
        if (!configService.isBackgroundActivityCaptureEnabled()) {
            return null
        }
        return payloadMessageCollator.buildFinalSessionMessage(
            FinalEnvelopeParams(
                initial = initial,
                endType = SessionSnapshotType.PERIODIC_CACHE,
                logger = logger
            )
        )
    }
}
