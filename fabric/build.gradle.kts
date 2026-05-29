plugins {
    id("com.possible-triangle.fabric")
}

fabric {
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
        url = uri("https://maven.jamieswhiteshirt.com/libs-release")
        content {
            includeGroup("com.jamieswhiteshirt")
        }
    }
}

dependencies {
    modInclude(libs.registrate.fabric)
    modInclude(libs.galena.hats.fabric)
    modInclude(libs.multikulti.core.fabric)
    modInclude(libs.multikulti.registrate.fabric)
    modInclude(libs.config.api.port.fabric)

    modCompileOnly(libs.jei.common.api)
    modCompileOnly(libs.jei.fabric.api)
    modCompileOnly(libs.jei.lib)

    // TODO re-add once create fabric is updated to 1.21.1
    // modCompileOnly("com.simibubi.create:create-fabric-${mc_version}:${create_fabric_version}") {
    //     exclude("com.jozufozu.flywheel")
    // }

    if (!env.isCI) {
        modRuntimeOnly(libs.jei.fabric)
        modRuntimeOnly(pack.fabric.modrinth.moonlight)
        modRuntimeOnly(pack.fabric.modrinth.supplementaries)
        modRuntimeOnly(pack.fabric.modrinth.just.enough.effect.descriptions.jeed)
    }
}
