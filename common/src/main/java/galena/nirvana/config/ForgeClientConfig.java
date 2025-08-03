package galena.nirvana.config;

import galena.nirvana.NirvanaConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class ForgeClientConfig implements NirvanaClientConfig {

    private final BooleanValue renderPeaceShader;

    public ForgeClientConfig(ForgeConfigSpec.Builder builder) {
        builder.push(NirvanaConstants.MOD_ID);

        this.renderPeaceShader = builder
                .comment("render shader when wearing a reefer head or under the peace effect")
                .define("renderPeaceShader", true);

        builder.pop();
    }

    @Override
    public boolean renderPeaceShader() {
        return renderPeaceShader.get();
    }

}
