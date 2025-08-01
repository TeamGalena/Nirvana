package galena.nirvana.forge.services;

import galena.nirvana.config.ForgeClientConfig;
import galena.nirvana.config.ForgeCommonConfig;
import galena.nirvana.config.NirvanaClientConfig;
import galena.nirvana.config.NirvanaCommonConfig;
import galena.nirvana.platform.services.IConfigs;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ForgeConfigs implements IConfigs {

    private static final Pair<ForgeCommonConfig, ModConfigSpec> COMMON = new ModConfigSpec.Builder().configure(ForgeCommonConfig::new);
    private static final Pair<ForgeClientConfig, ModConfigSpec> CLIENT = new ModConfigSpec.Builder().configure(ForgeClientConfig::new);

    @Override
    public NirvanaCommonConfig common() {
        return COMMON.getLeft();
    }

    @Override
    public NirvanaClientConfig client() {
        return CLIENT.getLeft();
    }

    public static void register(ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, COMMON.getRight());
        container.registerConfig(ModConfig.Type.CLIENT, CLIENT.getRight());
    }

}
