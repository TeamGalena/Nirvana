val mc_version: String by extra
val registrate_fabric_version: String by extra
val mixin_extras_version: String by extra
val forge_config_port_version: String by extra

plugins {
    id("dev.architectury.loom") version ("1.10-SNAPSHOT")
}

common {
    applyVanillaGradle = false
}

dependencies {
   "minecraft"("com.mojang:minecraft:${mc_version}")
   "mappings"(loom.officialMojangMappings())

    compileOnly("io.github.llamalad7:mixinextras-common:${mixin_extras_version}")

    modCompileOnly("fuzs.forgeconfigapiport:forgeconfigapiport-common:${forge_config_port_version}")

    modCompileOnly("com.tterrag.registrate_fabric:Registrate:${registrate_fabric_version}")
}

tasks.register("prepareWorkspace") {
    doFirst {
        logger.info("Somehow this task is needed")
    }
}