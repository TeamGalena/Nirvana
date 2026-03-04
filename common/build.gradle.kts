val mc_version: String by extra
val forge_config_port_version: String by extra

plugins {
    id("com.possible-triangle.architectury")
}

dependencies {
    modCompileOnly(libs.jei.common.api)
    modCompileOnly(libs.config.api.port.common)

    modCompileOnly(libs.registrate.fabric)
    modCompileOnly(libs.multikulti.registrate.fabric)
}