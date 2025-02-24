package io.embrace.android.embracesdk.internal.injection

import io.embrace.android.embracesdk.internal.worker.BackgroundWorker
import io.embrace.android.embracesdk.internal.worker.PriorityWorker
import io.embrace.android.embracesdk.internal.worker.Worker
import java.io.Closeable
import java.util.concurrent.atomic.AtomicReference

/**
 * A set of shared executors to be used throughout the SDK
 */
interface WorkerThreadModule : Closeable {

    /**
     * Return a [BackgroundWorker] matching the [worker]
     */
    fun backgroundWorker(worker: Worker.Background): BackgroundWorker

    /**
     * Return a [PriorityWorker] matching the [worker]
     */
    fun <T> priorityWorker(worker: Worker.Priority): PriorityWorker<T>

    /**
     * Returns the thread that monitors the main thread for ANRs
     */
    val anrMonitorThread: AtomicReference<Thread>

    /**
     * This should only be invoked when the SDK is shutting down. Closing all the worker threads in production means the
     * SDK will not be functional afterwards.
     */
    override fun close()
}
