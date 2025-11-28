pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "LogKCP"

include(":testApp")

includeBuild("plugins") {
    dependencySubstitution {
        substitute(module("com.maximums.logkcp:compilerPlugin"))
            .using(project(":compilerPlugin"))

        substitute(module("com.maximums.logkcp:runtime"))
            .using(project(":runtime"))

        substitute(module("com.maximums.logkcp:com.maximums.logkcp.gradle.plugin"))
            .using(project(":gradlePlugin"))
    }
}
