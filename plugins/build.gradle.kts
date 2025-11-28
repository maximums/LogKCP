plugins {
    // Define the Kotlin version ONCE here for all subprojects
    // Use 'apply false' so it doesn't apply the actual plugin to the root, just sets the version
    alias(libs.plugins.kotlin.jvm) apply false
}