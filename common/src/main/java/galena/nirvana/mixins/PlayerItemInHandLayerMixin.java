package galena.nirvana.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import galena.nirvana.index.NirvanaItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerItemInHandLayer.class)
public class PlayerItemInHandLayerMixin {

    @Inject(
            method = "renderArmWithItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void isUsingJoint(LivingEntity user, ItemStack stack, ItemDisplayContext context, HumanoidArm arm, PoseStack poseStack, MultiBufferSource bufferSource, int i, CallbackInfo ci) {
        if (NirvanaItems.JOINT.isIn(stack) && user.getUseItem() == stack && user.swingTime == 0) {
            var accessor = (PlayerItemInHandLayerAccessor) this;
            accessor.invokeRenderArmWithSpyglass(user, stack, arm, poseStack, bufferSource, i);
            ci.cancel();
        }
    }

}
