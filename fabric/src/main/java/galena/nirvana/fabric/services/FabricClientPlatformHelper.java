package galena.nirvana.fabric.services;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import galena.nirvana.client.CustomItemModel;
import galena.nirvana.fabric.client.CustomModelRenderer;
import galena.nirvana.platform.services.IClientPlatformHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.Item;

public class FabricClientPlatformHelper implements IClientPlatformHelper {

    @Override
    public CustomItemModel registerCustomModel(NonNullSupplier<? extends Item> item, CustomItemModel model) {
        BuiltinItemRendererRegistry.INSTANCE.register(item.get(), new CustomModelRenderer(model));
        var keys = model.models().stream().map(ModelResourceLocation::id).toList();
        ModelLoadingPlugin.register(context -> context.addModels(keys));
        return model;
    }

    @Override
    public String modelVariant() {
        return "fabric_resource";
    }

}
