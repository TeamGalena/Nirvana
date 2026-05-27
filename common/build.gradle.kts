val mc_version: String by extra
val forge_config_port_version: String by extra

plugins {
    id("com.possible-triangle.common")
}

dependencies {
    modCompileOnly(libs.jei.common.api)
    modCompileOnly(libs.config.api.port.common)

    modCompileOnly(libs.neoforge.stub)
    modCompileOnly(libs.registrate.neoforge)
    modCompileOnly(libs.multikulti.registrate.common)
}
