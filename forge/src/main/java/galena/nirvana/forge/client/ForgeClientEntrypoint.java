package galena.nirvana.forge.client;

import galena.nirvana.client.CustomItemModel;
import galena.nirvana.world.block.renderer.ReeferHeadRenderer;
import galena.nirvana.world.entity.renderer.ReeferRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ForgeClientEntrypoint {

    public static void init(IEventBus modBus) {
        CustomItemModel.register();

        modBus.addListener(ForgeClientEntrypoint::registerLayers);
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ReeferRenderer.LAYER, ReeferRenderer::createLayers);
        event.registerLayerDefinition(ReeferHeadRenderer.LAYER, ReeferHeadRenderer::createLayers);
    }

}
