plugins {
    id("com.possible-triangle.neoforge")
}

mod {
    mods.include(libs.registrate.neoforge)
    mods.include(libs.galena.hats.neoforge)
    mods.include(libs.multikulti.core.neoforge)
    mods.include(libs.multikulti.registrate.neoforge)
}

neoforge {
    dependOn(project(":common"))
}

repositories {
    maven {
        url = uri("https://mvn.devos.one/snapshots")
        content {
            includeGroup("com.tterrag.registrate")
        }
    }

    maven {
        url = uri("https://maven.createmod.net")
        content {
            includeGroup("com.simibubi.create")
            includeGroup("net.createmod.ponder")
            includeGroup("dev.engine-room.flywheel")
        }
    }

    maven {
        url = uri("https://maven.teamabnormals.com/")
        content {
            includeGroup("com.teamabnormals")
        }
    }
}

dependencies {
    modCompileOnly(libs.jei.common.api)
    modCompileOnly(libs.jei.neoforge.api)
    modCompileOnly(libs.jei.lib)
    modImplementation(libs.create.neoforge) {
        isTransitive = false
    }

    if (!env.isCI) {
        modRuntimeOnly(libs.jei.neoforge)
        modRuntimeOnly(libs.oreganized)
        modRuntimeOnly(pack.forge.modrinth.just.enough.effect.descriptions.jeed)
        modRuntimeOnly(pack.forge.modrinth.farmers.delight)
        modRuntimeOnly(pack.forge.modrinth.supplementaries)
        modRuntimeOnly(pack.forge.modrinth.moonlight)
        modRuntimeOnly(pack.forge.modrinth.freecam)
        modRuntimeOnly(pack.forge.modrinth.data.trades)
    }
}