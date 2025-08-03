package galena.nirvana.fabric.client;

import galena.nirvana.client.CustomItemModel;
import galena.nirvana.world.block.renderer.ReeferHeadRenderer;
import galena.nirvana.world.entity.renderer.ReeferRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CustomItemModel.register();

        EntityModelLayerRegistry.registerModelLayer(ReeferRenderer.LAYER, ReeferRenderer::createLayers);
        EntityModelLayerRegistry.registerModelLayer(ReeferHeadRenderer.LAYER, ReeferHeadRenderer::createLayers);
    }

}
