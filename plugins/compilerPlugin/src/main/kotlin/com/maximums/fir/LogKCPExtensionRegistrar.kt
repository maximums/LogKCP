package com.maximums.fir

import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class LogKCPExtensionRegistrar : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +::LogKCPObjectGenerator
    }
}
