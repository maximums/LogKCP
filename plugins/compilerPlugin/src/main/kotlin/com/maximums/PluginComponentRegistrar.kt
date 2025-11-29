package com.maximums

import com.maximums.fir.LogKCPExtensionRegistrar
import com.maximums.ir.LogKCPIrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.config.CommonConfigurationKeys
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter

class PluginComponentRegistrar : CompilerPluginRegistrar() {
    override val supportsK2: Boolean
        get() = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        configuration.get(LogKCPCommandLineProcessor.IR_DUMP_FILE_PATH_KEY)?.let { file ->
            val messageCollector = configuration.get(CommonConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
            val fileMsgCollector = FileMessageCollector(filePath = file, delegate = messageCollector)

            FirExtensionRegistrarAdapter.registerExtension(extension = LogKCPExtensionRegistrar())
            IrGenerationExtension.registerExtension(extension = LogKCPIrGenerationExtension(messageCollector = fileMsgCollector))
        }
    }
}

