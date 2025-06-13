package galena.nirvana.forge;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import galena.nirvana.NirvanaCommon;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.NirvanaTrades;
import galena.nirvana.forge.client.ForgeClientEntrypoint;
import galena.nirvana.forge.services.ForgeConfigs;
import galena.nirvana.forge.world.AddItemLootModifier;
import galena.nirvana.forge.world.ReplaceItemLootModifier;
import galena.nirvana.index.NirvanaBrewing;
import java.util.ArrayList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(NirvanaConstants.MOD_ID)
public class ForgeEntrypoint {

    public static final NonNullSupplier<ForgeNirvanaRegistrate> REGISTRATE = NonNullSupplier.lazy(() -> ForgeNirvanaRegistrate.create(NirvanaConstants.MOD_ID));

    public ForgeEntrypoint() {
        ForgeConfigs.register(ModLoadingContext.get());
        NirvanaCommon.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::registerTrades);

        //noinspection Convert2MethodRef
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ForgeClientEntrypoint.init());

        REGISTRATE.get()
                .object("replace_item")
                .generic(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> ReplaceItemLootModifier.CODEC)
                .register();

        REGISTRATE.get()
                .object("add_item")
                .generic(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> AddItemLootModifier.CODEC)
                .register();
    }

    private void setup(FMLCommonSetupEvent event) {
        NirvanaBrewing.register();
    }

    private void registerTrades(VillagerTradesEvent event) {
        NirvanaTrades.register((profession, level, listing) -> {
            if (event.getType() != profession) return;
            var trades = event.getTrades().computeIfAbsent(level, $ -> new ArrayList<>());
            trades.add(listing);
        });
    }

}
