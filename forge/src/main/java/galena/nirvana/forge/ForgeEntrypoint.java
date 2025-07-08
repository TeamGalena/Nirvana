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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;

@Mod(NirvanaConstants.MOD_ID)
public class ForgeEntrypoint {

    public static final NonNullSupplier<ForgeNirvanaRegistrate> REGISTRATE = NonNullSupplier.lazy(() -> ForgeNirvanaRegistrate.create(NirvanaConstants.MOD_ID));

    public ForgeEntrypoint(IEventBus modBus, Dist dist) {
        ForgeConfigs.register(modBus);
        NirvanaCommon.init();
        modBus.addListener(this::setup);
        NeoForge.EVENT_BUS.addListener(this::registerTrades);

        if (dist == Dist.CLIENT) {
            ForgeClientEntrypoint.init(modBus);
        }

        REGISTRATE.get()
                .object("replace_item")
                .generic(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> ReplaceItemLootModifier.CODEC)
                .register();

        REGISTRATE.get()
                .object("add_item")
                .generic(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> AddItemLootModifier.CODEC)
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
