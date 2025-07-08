package galena.nirvana.forge.client;

import com.mojang.blaze3d.vertex.PoseStack;
import galena.nirvana.client.CustomItemModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class CustomModelExtensions implements IClientItemExtensions {

    private final CustomItemModel model;
    private final Renderer RENDERER = new Renderer();

    public CustomModelExtensions(CustomItemModel model) {
        this.model = model;
    }

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return RENDERER;
    }

    private class Renderer extends BlockEntityWithoutLevelRenderer {

        public Renderer() {
            super(null, null);
        }

        @Override
        public void renderByItem(ItemStack stack, ItemDisplayContext mode, PoseStack pose, MultiBufferSource vertexConsumers, int light, int overlay) {
            model.render(stack, mode, pose, vertexConsumers, ((renderer, model, vertexConsumer) ->
                    renderer.renderModelLists(model, stack, light, overlay, pose, vertexConsumer))
            );
        }

    }

}
