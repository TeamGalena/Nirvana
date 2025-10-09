pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

plugins {
    id("com.possible-triangle.helper") version ("1.0.48")
}

include("common", "fabric", "forge")
