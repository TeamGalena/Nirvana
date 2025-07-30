val mc_version: String by extra

plugins {
    id("com.possible-triangle.gradle") version ("0.2.16")
}

subprojects {
    repositories {
        modrinthMaven()
        mavenLocal()

        maven {
            url = uri("https://mvn.devos.one/snapshots/")
            content {
                includeGroup("com.tterrag.registrate_fabric")
                includeGroup("io.github.fabricators_of_create.Porting-Lib")
            }
        }

        maven {
            url = uri("https://maven.tterrag.com/")
            content {
                includeGroup("com.tterrag.registrate")
            }
        }

        maven {
            url = uri("https://maven.blamejared.com/")
            content {
                includeGroup("mezz.jei")
            }
        }

        maven {
            url = uri("https://jitpack.io")
            content {
                includeGroup("com.github.llamalad7.mixinextras")
            }
        }

        maven {
            url = uri("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
            content {
                includeGroup("net.minecraftforge")
                includeGroup("fuzs.forgeconfigapiport")
            }
        }

        nexus {
            content {
                includeGroup("dev.galena")
                includeGroup("com.possible-triangle")
            }
        }
    }

    enablePublishing {
        repositories {
            if (env.isCI) nexus()
        }
    }

    configurations.all {
        resolutionStrategy {
            fun portingLib(module: String, patch: Int = 66) {
                force("io.github.fabricators_of_create.Porting-Lib:$module:3.1.0-beta.$patch+$mc_version")
            }
            portingLib("tags")
            portingLib("models", 65)
            portingLib("data")
            portingLib("lazy_registration", 54)
            portingLib("model_loader")
            portingLib("transfer")
            portingLib("common")
            portingLib("conditions")
            portingLib("fluids")
            portingLib("core")
            portingLib("gametest")
        }
    }
}

enableSonarQube()
enableSpotless()
