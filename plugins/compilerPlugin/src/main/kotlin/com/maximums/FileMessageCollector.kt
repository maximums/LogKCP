package com.maximums

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import java.io.File

class FileMessageCollector(
    filePath: String,
    private val delegate: MessageCollector
) : MessageCollector by delegate {

    private val file = File(filePath).also { file ->
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            file.createNewFile()
        }
    }

    @Synchronized
    override fun report(severity: CompilerMessageSeverity, message: String, location: CompilerMessageSourceLocation?) {
        val locString = if (location != null) " at ${location.path}:${location.line}:${location.column}" else ""

        file.appendText("$n[$severity]$locString$n$message$n")

        if (severity.isError || severity == CompilerMessageSeverity.STRONG_WARNING || severity == CompilerMessageSeverity.WARNING) {
            delegate.report(severity, "Logged to ${file.name}: ${message.take(100)}...", location)
        }
    }

    private inline val n: String
        get() = System.lineSeparator()
}
