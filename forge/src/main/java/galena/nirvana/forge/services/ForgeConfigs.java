package galena.nirvana.forge.services;


import galena.nirvana.config.ForgeClientConfig;
import galena.nirvana.config.ForgeCommonConfig;
import galena.nirvana.config.NirvanaClientConfig;
import galena.nirvana.config.NirvanaCommonConfig;
import galena.nirvana.platform.services.IConfigs;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ForgeConfigs implements IConfigs {

    private static final Pair<ForgeCommonConfig, ForgeConfigSpec> COMMON = new ForgeConfigSpec.Builder().configure(ForgeCommonConfig::new);
    private static final Pair<ForgeClientConfig, ForgeConfigSpec> CLIENT = new ForgeConfigSpec.Builder().configure(ForgeClientConfig::new);

    @Override
    public NirvanaCommonConfig common() {
        return COMMON.getLeft();
    }

    @Override
    public NirvanaClientConfig client() {
        return CLIENT.getLeft();
    }

    public static void register(ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, COMMON.getRight());
        context.registerConfig(ModConfig.Type.CLIENT, CLIENT.getRight());
    }

}
