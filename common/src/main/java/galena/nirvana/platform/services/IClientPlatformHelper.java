package galena.nirvana.platform.services;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import galena.nirvana.client.CustomItemModel;
import java.util.Collection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;

public interface IClientPlatformHelper {

    CustomItemModel registerCustomModel(NonNullSupplier<? extends Item> item, CustomItemModel model);

    default CustomItemModel registerCustomModel(ItemEntry<? extends Item> item, Collection<ItemDisplayContext> contexts) {
        var name = item.getId().getPath();
        return registerCustomModel(item, new CustomItemModel(name, contexts));
    }

    String modelVariant();

}
