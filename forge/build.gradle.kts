val mc_version: String by extra
val registrate_forge_version: String by extra
val jei_version: String by extra
val jeed_forge_version: String by extra
val fd_forge_version: String by extra
val moonlight_forge_version: String by extra
val supplementaries_forge_version: String by extra
val create_forge_version: String by extra
val freecam_forge_version: String by extra
val oreganized_version: String by extra
val blueprint_version: String by extra
val data_trades_version: String by extra
val galena_hats_version: String by extra
val multikulti_version: String by extra

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
}

dependencies {
    modCompileOnly(libs.jei.common.api)
    modCompileOnly(libs.jei.neoforge.api)
    modCompileOnly(libs.jei.lib)
    modImplementation(variantOf(libs.create.neoforge) {
        classifier("all")
    }) {
        isTransitive = false
    }

    if (!env.isCI) {
        modRuntimeOnly(libs.jei.neoforge)
        modRuntimeOnly("maven.modrinth:just-enough-effect-descriptions-jeed:${jeed_forge_version}")
        modRuntimeOnly("maven.modrinth:farmers-delight:${fd_forge_version}")
        modRuntimeOnly("maven.modrinth:supplementaries:${supplementaries_forge_version}")
        modRuntimeOnly("maven.modrinth:moonlight:${moonlight_forge_version}")
        modRuntimeOnly("maven.modrinth:freecam:${freecam_forge_version}")
        // modRuntimeOnly("dev.galena:oreganized:${oreganized_version}:slim")
        modRuntimeOnly("maven.modrinth:blueprint:${blueprint_version}")
        modRuntimeOnly("maven.modrinth:data-trades:${data_trades_version}")
    }
}