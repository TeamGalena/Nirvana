package galena.nirvana.forge.services;

import galena.nirvana.config.ForgeCommonConfig;
import galena.nirvana.config.NirvanaCommonConfig;
import galena.nirvana.platform.services.IConfigs;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ForgeConfigs implements IConfigs {

    private static final Pair<ForgeCommonConfig, ModConfigSpec> COMMON = new ModConfigSpec.Builder().configure(ForgeCommonConfig::new);

    @Override
    public NirvanaCommonConfig common() {
        return COMMON.getLeft();
    }

    public static void register(IEventBus modBus) {
        modBus.registerConfig(ModConfig.Type.COMMON, COMMON.getRight());
    }

}
