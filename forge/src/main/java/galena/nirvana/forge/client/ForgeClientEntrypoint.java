package galena.nirvana.forge.client;

import galena.nirvana.client.CustomItemModel;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.world.block.renderer.ReeferHeadRenderer;
import galena.nirvana.world.entity.renderer.ReeferRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

public class ForgeClientEntrypoint {

    public static void init(IEventBus modBus) {
        CustomItemModel.register();

        modBus.addListener(ForgeClientEntrypoint::registerLayers);
        modBus.addListener(ForgeClientEntrypoint::registerClientExtensions);
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ReeferRenderer.LAYER, ReeferRenderer::createLayers);
        event.registerLayerDefinition(ReeferHeadRenderer.LAYER, ReeferHeadRenderer::createLayers);
    }

    private static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new CustomModelExtensions(CustomItemModel.DEERSTALKER), NirvanaItems.DEERSTALKER);
        event.registerItem(new CustomModelExtensions(CustomItemModel.JOINT), NirvanaItems.JOINT);
    }

}
