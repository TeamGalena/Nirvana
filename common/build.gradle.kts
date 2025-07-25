val mc_version: String by extra
val registrate_fabric_version: String by extra
val forge_config_port_version: String by extra
val multikulti_version: String by extra

plugins {
    id("dev.architectury.loom") version ("1.10-SNAPSHOT")
}

common {
    applyVanillaGradle = false
}

dependencies {
   "minecraft"("com.mojang:minecraft:${mc_version}")
   "mappings"(loom.officialMojangMappings())

    compileOnly("org.ow2.asm:asm-tree:9.5")

    modCompileOnly("fuzs.forgeconfigapiport:forgeconfigapiport-common:${forge_config_port_version}")

    modCompileOnly("com.tterrag.registrate_fabric:Registrate:${registrate_fabric_version}")
    modCompileOnly("com.possible-triangle:multikulti-registrate-fabric:${mc_version}-${multikulti_version}")
}

tasks.register("prepareWorkspace") {
    doFirst {
        logger.info("Somehow this task is needed")
    }
}