package galena.nirvana.forge.client;

import galena.nirvana.client.CustomItemModel;
import galena.nirvana.world.block.renderer.ReeferHeadRenderer;
import galena.nirvana.world.entity.renderer.ReeferRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ForgeClientEntrypoint {

    public static void init() {
        CustomItemModel.register();

        var modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(ForgeClientEntrypoint::registerLayers);
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ReeferRenderer.LAYER, ReeferRenderer::createLayers);
        event.registerLayerDefinition(ReeferHeadRenderer.LAYER, ReeferHeadRenderer::createLayers);
    }

}
