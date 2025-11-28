plugins {
    `java-gradle-plugin`
    `kotlin-dsl`

    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.build.config)
    alias(libs.plugins.kotlin.binary.compatibility.validator)
}

group = "com.maximums.logkcp"
version = "0.1.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
}

buildConfig {
    useKotlinOutput {
        internalVisibility = true
    }

    val compilerPlugin = project(":compilerPlugin")

    packageName = "com.maximums"
    buildConfigField(type = "String", name = "KOTLIN_PLUGIN_ID", value = "\"${libs.plugins.logkcp.get().pluginId}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"${compilerPlugin.group}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_NAME", "\"${compilerPlugin.name}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${compilerPlugin.version}\"")
    buildConfigField("String", "logkcpVersion", "\"${project.version}\"")
}

gradlePlugin {
    plugins {
        create("logkcp") {
            id = "com.maximums.logkcp"
            displayName = "Log KCP"
            description = "Kotlin Compiler Plugin for Logging"
            implementationClass = "com.maximums.LogKCPPlugin"
        }
    }
}
