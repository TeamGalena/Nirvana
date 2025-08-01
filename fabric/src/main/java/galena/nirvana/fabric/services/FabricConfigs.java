package galena.nirvana.fabric.services;

import static galena.nirvana.NirvanaConstants.MOD_ID;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import galena.nirvana.config.ForgeClientConfig;
import galena.nirvana.config.ForgeCommonConfig;
import galena.nirvana.config.NirvanaClientConfig;
import galena.nirvana.config.NirvanaCommonConfig;
import galena.nirvana.platform.services.IConfigs;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FabricConfigs implements IConfigs {

    private static final Pair<ForgeCommonConfig, ModConfigSpec> COMMON = new ModConfigSpec.Builder().configure(ForgeCommonConfig::new);
    private static final Pair<ForgeClientConfig, ModConfigSpec> CLIENT = new ModConfigSpec.Builder().configure(ForgeClientConfig::new);

    public static void register() {
        NeoForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, COMMON.getRight());
        NeoForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, CLIENT.getRight());
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
