plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.logkcp)
}

dependencies {
    implementation(libs.kotlin.std)
    implementation(libs.logkcp.runtime)
}