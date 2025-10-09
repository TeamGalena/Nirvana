package galena.nirvana.mixins;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.sugar.Local;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.index.NirvanaBlocks;
import galena.nirvana.world.block.renderer.ReeferHeadRenderer;

import java.util.Map;

import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.SkullBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkullBlockRenderer.class)
public class SkullBlockRendererMixin {

    @Inject(
            method = "getRenderType(Lnet/minecraft/world/level/block/SkullBlock$Type;Lnet/minecraft/world/item/component/ResolvableProfile;)Lnet/minecraft/client/renderer/RenderType;",
            cancellable = true,
            at = @At("HEAD")
    )
    private static void reeferRenderType(SkullBlock.Type type, ResolvableProfile profile, CallbackInfoReturnable<RenderType> cir) {
        if (type == NirvanaBlocks.REEFER_SKULL_TYPE) {
            cir.setReturnValue(RenderType.entityCutoutNoCullZOffset(ReeferHeadRenderer.TEXTURE));
        }
    }

    @Inject(
            method = "createSkullRenderers(Lnet/minecraft/client/model/geom/EntityModelSet;)Ljava/util/Map;",
            at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;")
    )
    private static void registerReeferModel(EntityModelSet set, CallbackInfoReturnable<Map<SkullBlock.Type, SkullModelBase>> cir, @Local ImmutableMap.Builder<SkullBlock.Type, SkullModelBase> builder) {
        try {
            builder.put(NirvanaBlocks.REEFER_SKULL_TYPE, new SkullModel(set.bakeLayer(ReeferHeadRenderer.LAYER)));
        } catch (IllegalArgumentException ex) {
            NirvanaConstants.LOGGER.warn("unable to create reefer skull model. This can happen if another mod already breaks before nirvana. If nothing else fails, please report this");
        }
    }

}
