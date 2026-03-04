plugins {
    id("com.possible-triangle.architectury")
}

dependencies {
    modCompileOnly(libs.config.api.port.common)

    modCompileOnly(libs.registrate.fabric)
    modCompileOnly(libs.multikulti.registrate.fabric)
}
