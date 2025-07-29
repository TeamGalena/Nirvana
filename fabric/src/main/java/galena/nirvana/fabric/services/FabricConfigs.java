package galena.nirvana.fabric.services;

import static galena.nirvana.NirvanaConstants.MOD_ID;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import galena.nirvana.config.ForgeClientConfig;
import galena.nirvana.config.ForgeCommonConfig;
import galena.nirvana.config.NirvanaClientConfig;
import galena.nirvana.config.NirvanaCommonConfig;
import galena.nirvana.platform.services.IConfigs;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class FabricConfigs implements IConfigs {

    private static final Pair<ForgeCommonConfig, ForgeConfigSpec> COMMON = new ForgeConfigSpec.Builder().configure(ForgeCommonConfig::new);
    private static final Pair<ForgeClientConfig, ForgeConfigSpec> CLIENT = new ForgeConfigSpec.Builder().configure(ForgeClientConfig::new);

    public static void register() {
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, COMMON.getRight());
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, CLIENT.getRight());
    }

    @Override
    public NirvanaCommonConfig common() {
        return COMMON.getLeft();
    }

    @Override
    public NirvanaClientConfig client() {
        return CLIENT.getLeft();
    }
}
