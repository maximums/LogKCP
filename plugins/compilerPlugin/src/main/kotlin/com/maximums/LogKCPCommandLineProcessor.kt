package com.maximums

import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

class LogKCPCommandLineProcessor : CommandLineProcessor {
    override val pluginId: String
        get() = BuildConfig.KOTLIN_PLUGIN_ID

    override val pluginOptions: Collection<AbstractCliOption> = listOf(
        CliOption(
            optionName = IR_DUMP_FILE,
            valueDescription = "File Path",
            description = "File where IR dump will be stored",
            required = false
        )
    )

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        when(option.optionName) {
            IR_DUMP_FILE -> configuration.put(IR_DUMP_FILE_PATH_KEY, value)
        }
    }

    companion object {
        const val IR_DUMP_FILE = "dumpFile"
        val IR_DUMP_FILE_PATH_KEY = CompilerConfigurationKey<String>("IR dump file path")
    }
}
