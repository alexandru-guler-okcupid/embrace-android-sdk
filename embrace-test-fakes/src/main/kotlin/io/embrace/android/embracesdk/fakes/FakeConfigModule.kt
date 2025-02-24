package io.embrace.android.embracesdk.fakes

import io.embrace.android.embracesdk.internal.config.ConfigService
import io.embrace.android.embracesdk.internal.injection.ConfigModule

class FakeConfigModule(
    override val configService: ConfigService = FakeConfigService()
) : ConfigModule
