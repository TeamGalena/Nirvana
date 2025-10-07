pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

plugins {
    id("com.possible-triangle.helper") version ("1.0.43")
}

include("common", "fabric", "forge")
