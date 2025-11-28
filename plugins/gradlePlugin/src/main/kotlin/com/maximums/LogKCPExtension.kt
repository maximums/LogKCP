package com.maximums

import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

abstract class LogKCPExtension @Inject constructor(
    objectFactory: ObjectFactory,
    projectLayout: ProjectLayout,
) {
    val irDumpFile: RegularFileProperty = objectFactory.fileProperty().convention(
        projectLayout.buildDirectory.file("logkcp/irDump.txt")
    )
}
