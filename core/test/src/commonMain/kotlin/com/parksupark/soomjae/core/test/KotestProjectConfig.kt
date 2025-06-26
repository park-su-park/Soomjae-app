package com.parksupark.soomjae.core.test

import com.parksupark.soomjae.core.test.di.coreTestModule
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension

object KotestProjectConfig : AbstractProjectConfig() {
    override val extensions: List<Extension> = listOf(koinExtension(coreTestModule))
}
