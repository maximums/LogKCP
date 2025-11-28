package com.maximums

import com.maximums.BuildConfig.logkcpVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies

class LogKCPPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
            extensions.create(LOGKCP_EXT_NAME, LogKCPExtension::class)
            pluginManager.apply(LogKCPKotlinPlugin::class.java)
//            target.dependencies {
//                add("testImplementation", "com.maximums.logkcp:logkcp:$logkcpVersion")
//            }
        }
    }

    companion object {
        const val LOGKCP_EXT_NAME = "logkcp"
    }
}
