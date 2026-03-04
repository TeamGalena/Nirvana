plugins {
    id("com.possible-triangle.fabric")
}

mod {
    mods.include(libs.registrate.fabric)
    mods.include(libs.multikulti.core.fabric)
    mods.include(libs.multikulti.registrate.fabric)
    mods.include(libs.galena.hats.fabric)
    mods.include(libs.config.api.port.fabric)
}

fabric {
    dataGen()

    dependOn(project(":common"))

    accessWidener()
}

repositories {
    maven {
        url = uri("https://mvn.devos.one/snapshots/")
        content {
            includeGroup("com.simibubi.create")
            includeGroup("io.github.tropheusj")
        }
    }

    maven {
        url = uri("https://maven.createmod.net")
        content {
            includeGroup("net.createmod.ponder")
            includeGroup("dev.engine-room.flywheel")
        }
    }

    maven {
        url = uri("https://mvn.devos.one/releases/")
        content {
            includeGroup("io.github.fabricators_of_create.Porting-Lib")
        }
    }

    maven {
        url = uri("https://maven.jamieswhiteshirt.com/libs-release")
        content {
            includeGroup("com.jamieswhiteshirt")
        }
    }
}

dependencies {
    modCompileOnly(libs.jei.common.api)
    modCompileOnly(libs.jei.fabric.api)

    modCompileOnly(libs.create.fabric) {
        exclude("com.jozufozu.flywheel")
    }

    modImplementation(libs.multikulti.datagen.fabric)

    if (!env.isCI) {
        modRuntimeOnly(libs.jei.fabric)
        modRuntimeOnly(pack.fabric.modrinth.just.enough.effect.descriptions.jeed)
    }
}
