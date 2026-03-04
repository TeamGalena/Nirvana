import org.spongepowered.asm.gradle.plugins.MixinExtension

plugins {
    id("com.possible-triangle.forge")
}

mod {
    mods.include(libs.registrate.forge)
    mods.include(libs.multikulti.core.forge)
    mods.include(libs.multikulti.registrate.forge)
    mods.include(libs.galena.hats.forge)
}

forge {
    enableMixins()

    dependOn(project(":common"))
}

configure<MixinExtension> {
    config("${mod.id.get()}.forge.mixins.json")
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
    modCompileOnly(libs.jei.common.api)
    modCompileOnly(libs.jei.forge.api)
    modImplementation(
        variantOf(libs.create.forge) {
            classifier("all")
        },
    ) {
        isTransitive = false
    }
    modImplementation(libs.ponder.forge)

    if (!env.isCI) {
        modRuntimeOnly(libs.jei.forge)
        modRuntimeOnly(libs.oreganized)
        modRuntimeOnly(pack.forge.modrinth.blueprint)
        modRuntimeOnly(pack.forge.modrinth.just.enough.effect.descriptions.jeed)
        modRuntimeOnly(pack.forge.modrinth.farmers.delight)
        modRuntimeOnly(pack.forge.modrinth.supplementaries)
        modRuntimeOnly(pack.forge.modrinth.moonlight)
        modRuntimeOnly(pack.forge.modrinth.freecam)
        modRuntimeOnly(pack.forge.modrinth.data.trades)
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
