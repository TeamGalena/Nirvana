val mc_version: String by extra
val registrate_fabric_version: String by extra
val forge_config_port_version: String by extra
val multikulti_version: String by extra

plugins {
    id("com.possible-triangle.architectury")
}

dependencies {
    modCompileOnly("fuzs.forgeconfigapiport:forgeconfigapiport-common:${forge_config_port_version}")

    modCompileOnly("com.tterrag.registrate_fabric:Registrate:${registrate_fabric_version}")
    modCompileOnly("com.possible-triangle:multikulti-registrate-fabric:${mc_version}-${multikulti_version}")
}