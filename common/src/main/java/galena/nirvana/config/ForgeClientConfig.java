package galena.nirvana.config;

import galena.nirvana.NirvanaConstants;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;

public class ForgeClientConfig implements NirvanaClientConfig {

    private final BooleanValue renderPeaceShader;

    public ForgeClientConfig(ModConfigSpec.Builder builder) {
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
