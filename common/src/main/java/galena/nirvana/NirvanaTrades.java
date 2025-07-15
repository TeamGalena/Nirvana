package galena.nirvana;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.index.NirvanaTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

public class NirvanaTrades {

    @FunctionalInterface
    public interface Registration {
        void register(VillagerProfession profession, int level, VillagerTrades.ItemListing listing);
    }

    private static VillagerTrades.ItemListing sell(NonNullSupplier<? extends Item> item, int price, int count) {
        return (entity, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, price), new ItemStack(item.get(), count), 12, 30, 0.05F);
    }

    private static VillagerTrades.ItemListing sell(TagKey<Item> tag, int price, int count) {
        return (entity, random) -> {
            var lookup = entity.level().holderLookup(Registries.ITEM);
            return lookup.get(tag)
                    .flatMap(holder -> holder.getRandomElement(random))
                    .map(Holder::value)
                    .map(item -> new MerchantOffer(new ItemCost(Items.EMERALD, price), new ItemStack(item, count), 12, 30, 0.05F))
                    .orElse(null);
        };
    }

    public static void register(Registration registration) {
        registration.register(VillagerProfession.LEATHERWORKER, 4, sell(NirvanaItems.DEERSTALKER, 10, 1));
        registration.register(VillagerProfession.LEATHERWORKER, 3, sell(NirvanaTags.BURLAP, 10, 4));
        registration.register(VillagerProfession.LEATHERWORKER, 2, sell(NirvanaItems.HEMP_CLOTH, 10, 4));
    }

}
