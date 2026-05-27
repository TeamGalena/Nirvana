pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}

plugins {
    id("com.possible-triangle.helper") version ("1.4")
    id("com.possible-triangle.packwiz") version ("1.4.+")
}

include("common")
loader("forge", "fabric")

fun loader(vararg names: String) =
    names.forEach {
        include(it)
        packwiz {
            packs.create(it) {
                from = file("$it/pack")
            }
        }
    }
