package galena.nirvana.forge.services;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import galena.nirvana.client.CustomItemModel;
import galena.nirvana.platform.services.IClientPlatformHelper;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.event.ModelEvent;

public class ForgeClientPlatformHelper implements IClientPlatformHelper {

    @Override
    public CustomItemModel registerCustomModel(NonNullSupplier<? extends Item> item, CustomItemModel model) {
        var modBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        modBus.addListener((ModelEvent.RegisterAdditional event) ->
            model.models().forEach(event::register)
        );
        return model;
    }

    @Override
    public String modelVariant() {
        return "standalone";
    }

}
