val mc_version: String by extra
val registrate_fabric_version: String by extra
val forge_config_port_version: String by extra
val jei_version: String by extra
val multikulti_version: String by extra

plugins {
    id("com.possible-triangle.architectury")
}

dependencies {
    modCompileOnly("mezz.jei:jei-${mc_version}-common-api:${jei_version}")
    modCompileOnly("fuzs.forgeconfigapiport:forgeconfigapiport-common-neoforgeapi:${forge_config_port_version}")

    modCompileOnly("com.tterrag.registrate_fabric:Registrate:${registrate_fabric_version}")
    modCompileOnly("com.possible-triangle:multikulti-registrate-fabric:${mc_version}-${multikulti_version}")
}