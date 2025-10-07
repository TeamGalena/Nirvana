import org.spongepowered.asm.gradle.plugins.MixinExtension

plugins {
    id("com.possible-triangle.forge")
}

val mc_version: String by extra
val mod_id: String by extra
val registrate_forge_version: String by extra
val jei_version: String by extra
val jeed_version: String by extra
val fd_forge_version: String by extra
val moonlight_forge_version: String by extra
val supplementaries_forge_version: String by extra
val create_forge_version: String by extra
val flywheel_forge_version: String by extra
val freecam_forge_version: String by extra
val ponder_forge_version: String by extra
val oreganized_version: String by extra
val blueprint_version: String by extra
val data_trades_version: String by extra
val galena_hats_version: String by extra
val multikulti_version: String by extra

mod {
    mods.include("com.tterrag.registrate:Registrate:${registrate_forge_version}")
    mods.include("com.possible-triangle:multikulti-core-forge:${mc_version}-${multikulti_version}")
    mods.include("com.possible-triangle:multikulti-registrate-forge:${mc_version}-${multikulti_version}")
    mods.include("dev.galena:hats-forge:${mc_version}-${galena_hats_version}")
}

forge {
    enableMixins()

    dependOn(project(":common"))
}

configure<MixinExtension> {
    config("$mod_id.forge.mixins.json")
}

repositories {
    maven {
        url = uri("https://maven.createmod.net")
        content {
            includeGroup("com.simibubi.create")
            includeGroup("net.createmod.ponder")
            includeGroup("dev.engine-room.flywheel")
        }
    }

    repositories {
        maven {
            url = uri("https://thedarkcolour.github.io/KotlinForForge/")
            content {
                includeGroup("thedarkcolour")
            }
        }
    }
}

dependencies {
    modCompileOnly("mezz.jei:jei-${mc_version}-common-api:${jei_version}")
    modCompileOnly("mezz.jei:jei-${mc_version}-forge-api:${jei_version}")
    modImplementation("com.simibubi.create:create-${mc_version}:${create_forge_version}:slim") {
        isTransitive = false
    }
    modImplementation("net.createmod.ponder:Ponder-Forge-${mc_version}:${ponder_forge_version}")

    if (!env.isCI) {
        modRuntimeOnly("mezz.jei:jei-${mc_version}-forge:${jei_version}")
        modRuntimeOnly("maven.modrinth:just-enough-effect-descriptions-jeed:${jeed_version}")
        modRuntimeOnly("maven.modrinth:farmers-delight:${fd_forge_version}")
        modRuntimeOnly("maven.modrinth:supplementaries:${supplementaries_forge_version}")
        modRuntimeOnly("maven.modrinth:moonlight:${moonlight_forge_version}")
        modRuntimeOnly("maven.modrinth:freecam:${freecam_forge_version}")
        modRuntimeOnly("dev.galena:oreganized:${oreganized_version}:slim")
        modRuntimeOnly("maven.modrinth:blueprint:${blueprint_version}")
        modRuntimeOnly("dev.engine-room.flywheel:flywheel-forge-${mc_version}:${flywheel_forge_version}")
        modRuntimeOnly("maven.modrinth:data-trades:${data_trades_version}")
    }
}

upload {
    curseforge {
        dependencies {
            required("kotlin-for-forge")
        }
    }

    modrinth {
        dependencies {
            dependencies {
                required("ordsPcFz")
            }
        }
    }
}