package galena.nirvana.forge;

import galena.nirvana.NirvanaCommon;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.NirvanaTrades;
import galena.nirvana.forge.client.ForgeClientEntrypoint;
import galena.nirvana.forge.services.ForgeConfigs;
import galena.nirvana.forge.world.AddItemLootModifier;
import galena.nirvana.forge.world.ReplaceItemLootModifier;
import galena.nirvana.index.NirvanaBrewing;
import java.util.ArrayList;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@Mod(NirvanaConstants.MOD_ID)
public class ForgeEntrypoint {

    public static final ForgeNirvanaRegistrate REGISTRATE = new ForgeNirvanaRegistrate(NirvanaConstants.MOD_ID);

    public ForgeEntrypoint(ModContainer container, IEventBus modBus, Dist dist) {
        ForgeConfigs.register(container);
        REGISTRATE.registerEventListeners(modBus);
        NirvanaCommon.init();
        NeoForge.EVENT_BUS.addListener(this::registerBrewing);
        NeoForge.EVENT_BUS.addListener(this::registerTrades);

        if (dist == Dist.CLIENT) {
            ForgeClientEntrypoint.init(modBus);
        }

        REGISTRATE
                .object("replace_item")
                .generic(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> ReplaceItemLootModifier.CODEC)
                .register();

        REGISTRATE
                .object("add_item")
                .generic(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> AddItemLootModifier.CODEC)
                .register();
    }

    private void registerBrewing(RegisterBrewingRecipesEvent event) {
        NirvanaBrewing.register(event.getBuilder());
    }

    private void registerTrades(VillagerTradesEvent event) {
        NirvanaTrades.register((profession, level, listing) -> {
            if (event.getType() != profession) return;
            var trades = event.getTrades().computeIfAbsent(level, $ -> new ArrayList<>());
            trades.add(listing);
        });
    }

}
