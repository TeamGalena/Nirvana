import net.fabricmc.loom.api.LoomGradleExtensionAPI

val mod_id: String by extra
val mc_version: String by extra
val registrate_fabric_version: String by extra
val jei_version: String by extra
val jeed_version: String by extra
val create_fabric_version: String by extra
val forge_config_port_version: String by extra
val galena_hats_version: String by extra
val multikulti_version: String by extra

fabric {
    enableMixins()
    dataGen()

    dependOn(project(":common"))
    includesMod("com.tterrag.registrate_fabric:Registrate:${registrate_fabric_version}")
    // includesMod("com.possible-triangle:multikulti-core-fabric:${mc_version}-${multikulti_version}")
    includesMod("com.possible-triangle:multikulti-registrate-fabric:${mc_version}-${multikulti_version}")
    includesMod("dev.galena:hats-fabric:${mc_version}-${galena_hats_version}")
    includesMod("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:${forge_config_port_version}")
}

configure<LoomGradleExtensionAPI> {
    accessWidenerPath.set(file("src/main/resources/$mod_id.accesswidener"))
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
    modCompileOnly("mezz.jei:jei-${mc_version}-common-api:${jei_version}")
    modCompileOnly("mezz.jei:jei-${mc_version}-fabric-api:${jei_version}")
    modCompileOnly("mezz.jei:jei-${mc_version}-lib:${jei_version}")

    modCompileOnly("com.simibubi.create:create-fabric-${"1.20.1"}:${create_fabric_version}") {
        exclude("com.jozufozu.flywheel")
    }

    modImplementation("com.possible-triangle:multikulti-datagen-fabric:${mc_version}-${multikulti_version}")

    if (!env.isCI) {
        modRuntimeOnly("mezz.jei:jei-${mc_version}-fabric:${jei_version}")
        modRuntimeOnly("maven.modrinth:just-enough-effect-descriptions-jeed:${jeed_version}")
    }
}

uploadToCurseforge()
uploadToModrinth()