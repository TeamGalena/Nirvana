plugins {
    id("com.possible-triangle.neoforge")
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
    modInclude(libs.registrate.neoforge)
    modInclude(libs.galena.hats.neoforge)
    modInclude(libs.multikulti.core.neoforge)
    modInclude(libs.multikulti.registrate.neoforge)

    modCompileOnly(libs.jei.common.api)
    modCompileOnly(libs.jei.neoforge.api)
    modCompileOnly(libs.jei.lib)
    modImplementation(
        variantOf(libs.create.neoforge) {
            classifier("slim")
        },
    ) {
        // This is silly, I should maybe just include flywheel & ponder myself
        exclude(group = "com.tterrag.registrate")
        exclude(group = "dev.architectury")
        exclude(group = "cc.tweaked")
        exclude(group = "info.journeymap")
        exclude(group = "maven.modrinth")
        exclude(group = "dev.ftb.mods")
        exclude(group = "dev.engine-room.vanillin")
        exclude(group = "top.theillusivec4.curios")
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
