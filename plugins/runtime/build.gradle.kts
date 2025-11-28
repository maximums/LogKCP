plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.build.config)
    alias(libs.plugins.kotlin.binary.compatibility.validator)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}