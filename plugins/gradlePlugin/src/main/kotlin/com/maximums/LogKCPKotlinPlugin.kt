package com.maximums

import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class LogKCPKotlinPlugin : KotlinCompilerPluginSupportPlugin {
    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>) = true

    override fun getCompilerPluginId(): String = BuildConfig.KOTLIN_PLUGIN_ID

    override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
        groupId = BuildConfig.KOTLIN_PLUGIN_GROUP,
        artifactId = BuildConfig.KOTLIN_PLUGIN_NAME,
        version = BuildConfig.KOTLIN_PLUGIN_VERSION
    )

    override fun applyToCompilation(
        kotlinCompilation: KotlinCompilation<*>
    ): Provider<List<SubpluginOption>> = with(kotlinCompilation.target) {
        val ext = project.extensions.getByType(LogKCPExtension::class)
        val filePath = ext.irDumpFile.asFile.map { it.absolutePath }

        project.provider {
            listOf(
                SubpluginOption(
                    key = "dumpFile",
                    lazyValue = lazy { filePath.get() }
                )
            )
        }
    }
}
