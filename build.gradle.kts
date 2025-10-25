plugins {
    id("com.possible-triangle.core")
    id("com.possible-triangle.fabric") apply false
    id("com.possible-triangle.forge") apply false
    id("com.possible-triangle.architectury") apply false
}

subprojects {
    apply(plugin = "com.possible-triangle.core")

    repositories {
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

    upload {
        maven {
            nexus()
        }
    }
}

enableSonarQube()
enableSpotless()
