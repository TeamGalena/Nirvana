package galena.nirvana.forge.world;

import galena.nirvana.world.item.DeerStalkerItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

public class ForgeDeerstalkerItem extends DeerStalkerItem {

    public ForgeDeerstalkerItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment)
                || enchantment.value().isSupportedItem(new ItemStack(Items.LEATHER_HELMET));
    }

}
