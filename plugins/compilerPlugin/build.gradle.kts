plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.build.config)
    alias(libs.plugins.kotlin.binary.compatibility.validator)
}

group = "com.maximums.logkcp"
version = "0.1.0"

kotlin {
    compilerOptions {
        optIn.add("org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi")
        optIn.add("org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI")
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly(kotlin("compiler"))
    compileOnly(kotlin("stdlib"))

    implementation(project(":runtime"))
}

buildConfig {
    useKotlinOutput {
        internalVisibility = true
    }

    packageName = "com.maximums"
    buildConfigField(type = "String", name = "KOTLIN_PLUGIN_ID", value = "\"${libs.plugins.logkcp.get().pluginId}\"")
}
