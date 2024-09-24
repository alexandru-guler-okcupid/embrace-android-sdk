package io.embrace.android.embracesdk.internal.injection

import io.embrace.android.embracesdk.internal.delivery.caching.PayloadCachingService
import io.embrace.android.embracesdk.internal.delivery.caching.PayloadCachingServiceImpl
import io.embrace.android.embracesdk.internal.delivery.execution.RequestExecutionService
import io.embrace.android.embracesdk.internal.delivery.execution.RequestExecutionServiceImpl
import io.embrace.android.embracesdk.internal.delivery.intake.IntakeService
import io.embrace.android.embracesdk.internal.delivery.intake.IntakeServiceImpl
import io.embrace.android.embracesdk.internal.delivery.resurrection.PayloadResurrectionService
import io.embrace.android.embracesdk.internal.delivery.resurrection.PayloadResurrectionServiceImpl
import io.embrace.android.embracesdk.internal.delivery.scheduling.SchedulingService
import io.embrace.android.embracesdk.internal.delivery.scheduling.SchedulingServiceImpl
import io.embrace.android.embracesdk.internal.worker.Worker

internal class DeliveryModule2Impl(
    configModule: ConfigModule,
    initModule: InitModule,
    workerThreadModule: WorkerThreadModule
) : DeliveryModule2 {

    override val intakeService: IntakeService? by singleton {
        if (configModule.configService.isOnlyUsingOtelExporters()) {
            return@singleton null
        }
        IntakeServiceImpl(
            checkNotNull(schedulingService),
            initModule.jsonSerializer,
            workerThreadModule.priorityWorker(Worker.Priority.FileCacheWorker)
        )
    }

    override val payloadResurrectionService: PayloadResurrectionService? by singleton {
        if (configModule.configService.isOnlyUsingOtelExporters()) {
            return@singleton null
        }
        PayloadResurrectionServiceImpl(checkNotNull(intakeService))
    }

    override val payloadCachingService: PayloadCachingService? by singleton {
        if (configModule.configService.isOnlyUsingOtelExporters()) {
            return@singleton null
        }
        PayloadCachingServiceImpl()
    }

    override val requestExecutionService: RequestExecutionService? by singleton {
        if (configModule.configService.isOnlyUsingOtelExporters()) {
            return@singleton null
        }
        RequestExecutionServiceImpl()
    }

    override val schedulingService: SchedulingService? by singleton {
        if (configModule.configService.isOnlyUsingOtelExporters()) {
            return@singleton null
        }
        SchedulingServiceImpl(checkNotNull(requestExecutionService))
    }
}
